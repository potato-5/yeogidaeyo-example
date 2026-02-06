package com.hyun.sesac.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyun.sesac.domain.model.KakaoSearchModel
import com.hyun.sesac.domain.model.Parking
import com.hyun.sesac.domain.result.ProductResult
import com.hyun.sesac.domain.usecase.map.GetCombinedParkingListUseCase
import com.hyun.sesac.domain.usecase.map.GetKakaoSearchPlaceUseCase
import com.hyun.sesac.domain.usecase.room.RoomParkingUseCase
import com.hyun.sesac.home.ui.state.ParkingMapUiState
import com.hyun.sesac.home.ui.state.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    getCombinedParkingListUseCase: GetCombinedParkingListUseCase,
    private val kakaoSearchPlaceUseCase: GetKakaoSearchPlaceUseCase,
    private val roomParkingUseCase: RoomParkingUseCase
): ViewModel() {
    private val _selectedSpotId = MutableStateFlow<String?>(null)
    private val _selectedQuery = MutableStateFlow("")
    private val _selectedResults = MutableStateFlow<List<KakaoSearchModel>>(emptyList())
    private val _searchedPlace = MutableStateFlow<KakaoSearchModel?>(null)
    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()
    val uiState: StateFlow<ParkingMapUiState> = combine(
        getCombinedParkingListUseCase(),
        _selectedSpotId,
        _selectedQuery,
        _selectedResults,
        _searchedPlace
    ) { combinedResult, selectedId, query, results, searchedPlace ->
        val isLoading = combinedResult is ProductResult.Loading
        val parkingList = if (combinedResult is ProductResult.Success) combinedResult.resultData else emptyList()
        val selectedSpot = parkingList.find { it.id == selectedId }

        ParkingMapUiState(
            parkingSpots = parkingList,
            selectedSpot = selectedSpot,
            isLoading = isLoading,
            searchQuery = query,
            searchResults = results,
            searchedLocation = searchedPlace
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ParkingMapUiState(isLoading = true)
        )

    init {
        refreshAllRealtimeData()
    }

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
            roomParkingUseCase.refreshBookmark()
        }
    }

    // 즐겨찾기 토글 (이제 로직이 매우 간단해짐)
    fun toggleBookmark() {
        // 현재 선택된 주차장 정보를 '합쳐진 데이터(uiState)'에서 가져옴
        val currentSpot = uiState.value.selectedSpot ?: return

        viewModelScope.launch {
            // 현재 상태가 찜이면 -> 삭제, 아니면 -> 추가
            val result = if (currentSpot.isBookmarked) {
                roomParkingUseCase.removeBookmark(currentSpot.id)
            } else {
                roomParkingUseCase.addBookmark(currentSpot.id)
            }

            // DB 작업 실패 시에만 토스트 메시지 띄움
            // (성공하면 Room DB가 바뀌고 -> combine이 다시 돌아서 -> UI가 자동 갱신됨)
            if (result !is ProductResult.Success) {
                _uiEvent.emit(UiEvent.ShowToast("즐겨찾기 변경에 실패했습니다."))
            }
        }
    }

    fun onSearchQueryChanged(query: String){
        _selectedQuery.value = query
    }

    fun onSearch(){
        val query = _selectedQuery.value
        if(query.isBlank()) return

        viewModelScope.launch {
            when(val result = kakaoSearchPlaceUseCase(query)){
                is ProductResult.Success -> {
                    _selectedResults.value = result.resultData
                }
                is ProductResult.NetworkError -> {
                    _uiEvent.emit(UiEvent.ShowToast("검색 실패: ${result.exception}"))
                    _selectedResults.value = emptyList()
                }else -> {}
            }
        }
    }

    fun onSearchPlaceSelected(searchModel: KakaoSearchModel){
        _searchedPlace.value = searchModel
        _selectedResults.value = emptyList()
    }

    fun clearSearchResults(){
        _selectedQuery.value = ""
        _selectedResults.value = emptyList()
        _searchedPlace.value = null
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
