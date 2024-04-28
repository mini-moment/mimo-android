package com.mimo.android.data.network.login

import android.content.Context
import com.mimo.android.data.response.ApiResponse
import com.mimo.android.data.response.LoginResponse
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object NaverLoginManager {

    private val _loginResult = MutableStateFlow<ApiResponse<LoginResponse>>(ApiResponse.Failure)
    val loginResult: StateFlow<ApiResponse<LoginResponse>> = _loginResult

    private val profileCallback = object : NidProfileCallback<NidProfileResponse> {
        override fun onSuccess(response: NidProfileResponse) {
            _loginResult.value = ApiResponse.Success(
                LoginResponse(
                    userName = response.profile?.name ?: "",
                    userContract = response.profile?.mobile ?: "",
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
    private val oauthLoginCallback = object : OAuthLoginCallback {
        override fun onSuccess() {
            NidOAuthLogin().callProfileApi(profileCallback)
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
