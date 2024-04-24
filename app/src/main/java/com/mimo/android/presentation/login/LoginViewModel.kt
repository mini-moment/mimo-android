package com.mimo.android.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mimo.android.data.network.login.NaverLoginManager
import com.mimo.android.data.repository.DataStoreRepository
import com.mimo.android.data.response.ApiResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginViewModel(private val dataStoreRepository: DataStoreRepository) : ViewModel() {

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
                        dataStoreRepository.saveAccessToken(loginResponse.data)
                        _event.emit(LoginEvent.Success)
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
}
