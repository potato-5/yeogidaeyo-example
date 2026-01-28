package com.hyun.sesac.home.ui.state

// TODO 12/22 marker ui state에서 관리
// TODO 12/22 변경(바뀌는) 데이터는 모두 uiState
// TODO 12/22 현재 current screen에 있는데 recomposition으로 hoising 해줘야 됨
data class CurrentLocationUiState(
   //var friendList : List<FriendInfo> = emptyList(),
    var isLoading : Boolean = false,
    var result: Boolean = false
)