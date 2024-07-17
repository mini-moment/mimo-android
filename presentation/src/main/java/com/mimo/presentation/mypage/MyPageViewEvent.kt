package com.mimo.presentation.mypage

sealed interface MyPageViewEvent {
    data object Logout : MyPageViewEvent

    data class Error(val errorMessage: String) : MyPageViewEvent

    data object UnRegister : MyPageViewEvent
}
