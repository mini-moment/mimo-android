package com.mimo.android.presentation.login

import com.mimo.android.util.ErrorCode


sealed interface LoginEvent {
    data object Success : LoginEvent
    data class Error(val errorCode: ErrorCode = ErrorCode.NONE, val errorMessage: String = "") : LoginEvent
}
