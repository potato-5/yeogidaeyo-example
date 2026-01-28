package com.hyun.sesac.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyun.sesac.domain.common.DataResourceResult
import com.hyun.sesac.domain.model.Parking
import com.hyun.sesac.domain.repository.BookmarkRepository
import com.hyun.sesac.domain.usecase.firestore.GetParkingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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
    // 전체 지도 마커용 ( firestore )
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
            /*val current = bookmarkRepository.getParking(currentId).first()
            current ?: return@launch

            if(current.isBookmarked){
                bookmarkRepository.removeBookmark(currentId)
            }else{
                bookmarkRepository.addBookmark(currentId)
            }*/
        }
    }
/*
    @OptIn(ExperimentalCoroutinesApi::class)
    val isBookmarked: StateFlow<Boolean> = selectedSpot
        .map { parking ->
            parking?.isBookmarked ?: false
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    fun toggleBookmark(parking: Parking?){
        if (parking == null) return // 없으면 그냥 무시하고 종료

        val currentStatus = isBookmarked.value
        viewModelScope.launch{
            if(currentStatus){
                bookmarkRepository.removeBookmark(parking.id)
            }else{
                bookmarkRepository.addBookmark(parking.id)
            }
        }
    }*/
}