package com.mimo.android.data.model.response

import com.mimo.android.util.ErrorCode

sealed class ApiResponse<out T : Any?> {
    data class Success<out T : Any?>(
        val data: T,
    ) : ApiResponse<T>()

    data class Error(
        val errorCode: ErrorCode = ErrorCode.NONE,
        val errorMessage: String = "",
    ) : ApiResponse<Nothing>()

    data object Failure : ApiResponse<Nothing>()
}

