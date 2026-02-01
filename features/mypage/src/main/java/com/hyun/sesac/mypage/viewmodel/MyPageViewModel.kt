package com.hyun.sesac.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyun.sesac.domain.repository.BookmarkRepository
import com.hyun.sesac.domain.repository.UserRepository
import com.hyun.sesac.domain.result.ProductResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    // TODO USECASE 넣어줘야 함 ( 02/01 )
    userRepository: UserRepository,
    bookmarkRepository: BookmarkRepository
): ViewModel() {

    val userInfo = userRepository.currentUser()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ProductResult.Loading
        )

    val favoriteList = bookmarkRepository.getBookmarkedList()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ProductResult.Loading
        )
}