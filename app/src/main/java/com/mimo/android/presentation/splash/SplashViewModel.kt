package com.mimo.android.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mimo.android.data.repository.DataStoreRepository
import com.mimo.android.data.response.ApiResponse
import com.mimo.android.presentation.login.LoginEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {
    private val _event = MutableSharedFlow<LoginEvent>()
    val event: SharedFlow<LoginEvent> = _event

    init {
        getUserPreferences()
    }

    private fun getUserPreferences() {
        viewModelScope.launch {
            dataStoreRepository.getUserToken().collectLatest { apiResponse ->
                when (apiResponse) {
                    is ApiResponse.Success -> _event.emit(LoginEvent.Success)

                    is ApiResponse.Error -> {
                        _event.emit(
                            LoginEvent.Error(
                                errorCode = apiResponse.errorCode,
                                errorMessage = apiResponse.errorMessage,
                            ),
                        )
                    }

                    is ApiResponse.Failure -> {}
                }
            }
        }
    }
}
