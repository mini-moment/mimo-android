package com.mimo.presentation.login

import android.content.Context
import com.mimo.domain.model.ApiResponse
import com.mimo.domain.model.User
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

object NaverLoginManager {

    private val _loginResult = MutableStateFlow<ApiResponse<User>>(ApiResponse.Failure)
    val loginResult: StateFlow<ApiResponse<User>> = _loginResult

    private val profileCallback = object : NidProfileCallback<NidProfileResponse> {
        override fun onSuccess(response: NidProfileResponse) {
            _loginResult.value = ApiResponse.Success(
                User(
                    userName = response.profile?.name ?: "",
                    userContact = response.profile?.mobile ?: "",
                    profileImageUrl = response.profile?.profileImage ?: "",
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
            Timber.tag("mini-moment").d("$errorCode $message")
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
            Timber.tag("mini-moment").d("$errorCode $message")
            onFailure(errorCode, message)
        }
    }

    fun login(context: Context) {
        NaverIdLoginSDK.authenticate(context, oauthLoginCallback)
    }
}
