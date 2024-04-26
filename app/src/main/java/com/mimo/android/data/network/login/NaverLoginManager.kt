package com.mimo.android.data.network.login

import android.content.Context
import com.mimo.android.data.response.ApiResponse
import com.mimo.android.data.response.LoginResponse
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object NaverLoginManager {

    private val _loginResult = MutableStateFlow<ApiResponse<LoginResponse>>(ApiResponse.Failure)
    val loginResult: StateFlow<ApiResponse<LoginResponse>> = _loginResult

    private val oauthLoginCallback = object : OAuthLoginCallback {
        override fun onSuccess() {
            _loginResult.value = ApiResponse.Success(
                LoginResponse(
                    accessToken = NaverIdLoginSDK.getAccessToken() ?: "",
                    refreshToken = NaverIdLoginSDK.getRefreshToken() ?: "",
                ),
            )
        }

        override fun onFailure(httpStatus: Int, message: String) {
            _loginResult.value = ApiResponse.Error(
                errorCode = httpStatus,
                errorMessage = message,
            )
        }

        override fun onError(errorCode: Int, message: String) {
            onFailure(errorCode, message)
        }
    }

    fun login(context: Context) {
        NaverIdLoginSDK.authenticate(context, oauthLoginCallback)
    }
}
