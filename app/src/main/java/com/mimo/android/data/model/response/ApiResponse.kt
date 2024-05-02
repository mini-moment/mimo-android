package com.mimo.android.data.model.response

import com.mimo.android.util.ErrorCode

sealed class ApiResponse<out T : Any?> {
    data class Success<out T : Any?>(
        val data: T,
    ) : ApiResponse<T>()

    data class Error(
        val errorCode: Int = 0,
        val errorMessage: String = "",
    ) : ApiResponse<Nothing>()

    data object Failure : ApiResponse<Nothing>()
}

