package com.mimo.presentation.login


sealed interface LoginEvent {
    data object Success : LoginEvent
    data class Error(val errorCode: Int = 0, val errorMessage: String = "") : LoginEvent
}
