package com.mimo.android.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mimo.android.data.network.login.NaverLoginManager
import com.mimo.android.data.repository.UserRepository
import com.mimo.android.data.request.UserRequest
import com.mimo.android.data.response.ApiResponse
import com.mimo.android.data.response.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _event = MutableSharedFlow<LoginEvent>()
    val event: SharedFlow<LoginEvent> = _event

    init {
        observerLoginResponse()
    }

    private fun observerLoginResponse() {
        viewModelScope.launch {
            NaverLoginManager.loginResult.collectLatest { loginResponse ->
                when (loginResponse) {
                    is ApiResponse.Success -> {
                        userSignUp(loginResponse.data)
                    }

                    is ApiResponse.Error -> {
                        _event.emit(
                            LoginEvent.Error(
                                errorCode = loginResponse.errorCode,
                                errorMessage = loginResponse.errorMessage,
                            ),
                        )
                    }

                    is ApiResponse.Failure -> {}
                }
            }
        }
    }

    private suspend fun userSignUp(loginResponse: LoginResponse) {
        userRepository.signUp(
            UserRequest(
                userName = loginResponse.userName,
                userContract = loginResponse.userContract,
                accessToken = loginResponse.accessToken,
                refreshToken = loginResponse.refreshToken,
            ),
        ).collectLatest { signUpResponse ->
            when (signUpResponse) {
                is ApiResponse.Success -> _event.emit(LoginEvent.Success)
                is ApiResponse.Failure -> {}
                is ApiResponse.Error -> _event.emit(
                    LoginEvent.Error(
                        errorCode = signUpResponse.errorCode,
                        errorMessage = signUpResponse.errorMessage,
                    ),
                )
            }
        }
    }
}
