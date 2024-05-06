package com.mimo.android.data.model.response

import retrofit2.Response

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

suspend fun <T> apiHandler(
    apiResponse: suspend () -> Pair<Response<T>, ErrorResponse?>,
): ApiResponse<T> {
    runCatching {
        val action = apiResponse.invoke()
        val response = action.first
        if (response.isSuccessful) {
            response.body()?.let { body ->
                return ApiResponse.Success(body)
            }
        } else {
            return ApiResponse.Error(
                errorCode = action.second?.statusCode ?: 0,
                errorMessage = action.second?.message ?: "",
            )
        }
    }.onFailure {
        return ApiResponse.Error(errorMessage = it.message ?: "")
    }
    return ApiResponse.Failure
}
