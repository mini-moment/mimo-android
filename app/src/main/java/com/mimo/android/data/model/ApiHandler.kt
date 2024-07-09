package com.mimo.android.data.model

import com.mimo.domain.model.ApiResponse
import com.mimo.domain.model.ErrorResponse
import retrofit2.Response

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
