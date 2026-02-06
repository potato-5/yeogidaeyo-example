package com.hyun.sesac.domain.usecase.room

import javax.inject.Inject

data class RoomParkingUseCase @Inject constructor(
    val getBookmarkList: GetBookmarkedListUseCase,
    val addBookmark: AddBookmarkUseCase,
    val removeBookmark: RemoveBookmarkUseCase,
    val refreshBookmark: RefreshRealtimeDataUseCase
)