package com.hyun.sesac.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyun.sesac.domain.common.DataResourceResult
import com.hyun.sesac.domain.model.Parking
import com.hyun.sesac.domain.repository.BookmarkRepository
import com.hyun.sesac.domain.result.ProductResult
import com.hyun.sesac.domain.usecase.firestore.GetParkingUseCase
import com.hyun.sesac.home.ui.state.ParkingMapUiState
import com.hyun.sesac.home.ui.state.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.forEach

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getParkingUseCase: GetParkingUseCase,
    private val bookmarkRepository: BookmarkRepository
): ViewModel() {
    // 1. 사용자 입력 (어떤 주차장을 선택했는지 ID만 기억)
    private val _selectedSpotId = MutableStateFlow<String?>(null)

    // 2. 일회성 이벤트 채널 (토스트 메시지용)
    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    // 3. [핵심] 모든 데이터를 하나로 합치는 파이프라인
    val uiState: StateFlow<ParkingMapUiState> = combine(
        getParkingUseCase(),                    // (A) Firestore 데이터 스트림
        bookmarkRepository.getBookmarkedList(), // (B) Room DB(찜) 데이터 스트림
        _selectedSpotId                         // (C) 현재 선택된 ID
    ) { firestoreResult, localResult, selectedId ->

        // (1) 로딩 체크: 둘 중 하나라도 로딩이면 로딩 상태
        val isLoading = firestoreResult is ProductResult.Loading || localResult is ProductResult.Loading

        // (2) 데이터 꺼내기 (상자깡)
        val baseList = if (firestoreResult is ProductResult.Success) firestoreResult.resultData else emptyList()
        val overlayList = if (localResult is ProductResult.Success) localResult.resultData else emptyList()

        // (3) 데이터 합치기 (Firestore 리스트 + Room 정보 덮어쓰기)
        // 빠른 검색을 위해 로컬 데이터를 Map으로 변환
        val overlayMap = overlayList.associateBy { it.id }

        val mergedList = baseList.map { original ->
            val overlay = overlayMap[original.id]
            if (overlay != null) {
                // Room에 데이터가 있으면 -> 찜 된 상태 + 실시간 대수 반영
                original.copy(
                    isBookmarked = true,
                    currentCnt = overlay.currentCnt
                )
            } else {
                // Room에 없으면 -> 찜 안 된 상태
                original.copy(isBookmarked = false)
            }
        }

        // (4) 선택된 주차장 객체 찾기 (합쳐진 리스트에서 찾으므로 데이터 정합성 100% 일치)
        val selectedSpot = mergedList.find { it.id == selectedId }

        // (5) 최종 UI 상태 배달
        ParkingMapUiState(
            parkingSpots = mergedList,
            selectedSpot = selectedSpot,
            isLoading = isLoading
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ParkingMapUiState(isLoading = true)
        )

    init {
        // 앱 켜질 때 실시간 데이터 갱신 요청
        refreshAllRealtimeData()
    }

    // --- 사용자 액션 함수들 ---

    // 지도 마커 클릭 시
    fun onSpotSelected(spot: Parking) {
        _selectedSpotId.value = spot.id
    }

    // 바텀시트 닫기 / 빈 곳 클릭 시
    fun cleanSpot() {
        _selectedSpotId.value = null
    }

    // 실시간 데이터 갱신
    fun refreshAllRealtimeData() {
        viewModelScope.launch {
            bookmarkRepository.refreshAllParkingData()
        }
    }

    // 즐겨찾기 토글 (이제 로직이 매우 간단해짐)
    fun toggleBookmark() {
        // 현재 선택된 주차장 정보를 '합쳐진 데이터(uiState)'에서 가져옴
        val currentSpot = uiState.value.selectedSpot ?: return

        viewModelScope.launch {
            // 현재 상태가 찜이면 -> 삭제, 아니면 -> 추가
            val result = if (currentSpot.isBookmarked) {
                bookmarkRepository.removeBookmark(currentSpot.id)
            } else {
                bookmarkRepository.addBookmark(currentSpot.id)
            }

            // DB 작업 실패 시에만 토스트 메시지 띄움
            // (성공하면 Room DB가 바뀌고 -> combine이 다시 돌아서 -> UI가 자동 갱신됨)
            if (result !is ProductResult.Success) {
                _uiEvent.emit(UiEvent.ShowToast("즐겨찾기 변경에 실패했습니다."))
            }
        }
    }
}
    // 전체 지도 마커용 ( firestore )
/*
    private val _parkingSpots = MutableStateFlow<List<Parking>>(emptyList())
    val parkingSpots: StateFlow<List<Parking>> = _parkingSpots.asStateFlow()

    // optimistic bookmark state용 stateflow
    private val _bookmarkState = MutableStateFlow(false)
    val bookmarkState: StateFlow<Boolean> = _bookmarkState.asStateFlow()

    // select된 주차장 정보 id
    private val _selectedSpotID = MutableStateFlow<String?>(null)
    @OptIn(ExperimentalCoroutinesApi::class)
    val selectedSpot: StateFlow<Parking?> = _selectedSpotID
        .flatMapLatest { id ->
            if (id == null) {
                flowOf(null)
            } else {
                // (1) 일단 Firestore 목록에서 해당 주차장을 찾음 (이름, 주소, 전체대수 보존)
                val firestoreSpot = _parkingSpots.value.find { it.id == id }

                if (firestoreSpot == null) {
                    flowOf(null) // Firestore에 없는 데이터면 null
                } else {
                    // (2) Room DB(즐겨찾기/실시간)를 관찰
                    bookmarkRepository.getParking(id).map { roomData ->
                        if (roomData != null) {
                            firestoreSpot.copy(
                                currentCnt = roomData.currentCnt,
                                isBookmarked = roomData.isBookmarked
                            )
                        } else {
                            firestoreSpot
                        }
                    }
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    init{
        loadParkingData()
        fetchAllRealtimeData()
    }

    fun onSpotSelected(spot: Parking){
        _selectedSpotID.value = spot.id
        viewModelScope.launch{
            val initialBookmark =
                bookmarkRepository.getParking(spot.id).first()?.isBookmarked ?: false

            _bookmarkState.value = initialBookmark
        }
    }

    fun cleanSpot(){
        _selectedSpotID.value = null
    }

    // firestore 데이터 로드
    fun loadParkingData(){
        viewModelScope.launch {
            getParkingUseCase().collectLatest { result ->
                when(result){
                    is DataResourceResult.Loading -> {
                        Log.d("Firestore", "데이터 로딩 중...")
                    }
                    is DataResourceResult.Success -> {
                        val spots = result.data
                        Log.d("Firestore", "데이터 로드 성공! 개수: ${spots.size}")
                        spots.forEach {
                            Log.d("Firestore", "주차장: ${it.name}, 좌표: ${it.latitude}, ${it.longitude}")
                        }
                        _parkingSpots.value = spots
                    }
                    is DataResourceResult.Failure -> {
                        Log.e("FireStore", "데이터 로드 실패: ${result.exception.message}")
                    }

                    DataResourceResult.DummyConstructor -> TODO()
                }
            }
        }
    }

    // 앱 켜질때, 1번 실시간 api 호출 -> db 저장
    fun fetchAllRealtimeData(){
        viewModelScope.launch{
            bookmarkRepository.refreshAllParkingData()
        }
    }

    // 즐겨찾기 토글
    fun toggleBookmark(){
        val currentId = selectedSpot.value?.id ?: return
        viewModelScope.launch{
            val prevState = _bookmarkState.value
            val newState = !prevState

            // optimistic ui
            _bookmarkState.value = newState

            try {
                if (newState) {
                    bookmarkRepository.addBookmark(currentId)
                } else {
                    bookmarkRepository.removeBookmark(currentId)
                }
            }catch(e: Exception){
                _bookmarkState.value = prevState
                Log.e("Bookmark", "즐겨찾기 실패", e)

                // TODO 스낵바, 토스트 이벤트 추가
            }

            val current = bookmarkRepository.getParking(currentId).first()
                current ?: return@launch

                if(current.isBookmarked){
                    bookmarkRepository.removeBookmark(currentId)
                }else{
                    bookmarkRepository.addBookmark(currentId)
                }
        }
    }
}*/
