package com.mimo.presentation.mypage

sealed interface MyPageViewEvent {
    data object Logout : MyPageViewEvent
}
