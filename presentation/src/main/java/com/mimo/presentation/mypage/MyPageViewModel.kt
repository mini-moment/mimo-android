package com.mimo.presentation.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mimo.domain.model.ApiResponse
import com.mimo.domain.model.Post
import com.mimo.domain.repository.DataStoreRepository
import com.mimo.domain.repository.PostRepository
import com.mimo.domain.repository.UserRepository
import com.mimo.presentation.login.NaverLoginManager
import com.mimo.presentation.util.ErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel
    @Inject
    constructor(
        private val postRepository: PostRepository,
        private val dataStoreRepository: DataStoreRepository,
        private val userRepository: UserRepository,
    ) : ViewModel() {
        private val _myPostList = MutableLiveData<List<Post>>()
        private val _event = MutableSharedFlow<MyPageViewEvent>()
        val event: SharedFlow<MyPageViewEvent> = _event

        val myPostList: LiveData<List<Post>> get() = _myPostList

        private fun setMyPostList(value: List<Post>) {
            _myPostList.value = value
        }

        fun getMyPost() {
            viewModelScope.launch {
                when (val response = postRepository.getMyPost()) {
                    is ApiResponse.Success -> {
                        setMyPostList(response.data)
                        Timber.d("나의 게시글 불러오기 성공! ${response.data}")
                    }

                    is ApiResponse.Error -> {
                        Timber.d("나의 게시글 불러오기 에러 발생! ${response.errorMessage}")
                    }

                    is ApiResponse.Failure -> {
                        Timber.d("나의 게시글 불러오기 알 수 없는 에러 발생!")
                    }
                }
            }
        }

        fun userLogout() {
            viewModelScope.launch {
                runCatching {
                    dataStoreRepository.deleteAccessToken()
                    dataStoreRepository.deleteRefreshToken()
                    NaverLoginManager.logout()
                }.onSuccess {
                    _event.emit(MyPageViewEvent.Logout)
                }.onFailure {
                    _event.emit(MyPageViewEvent.Error(ErrorMessage.LOGOUT_ERROR_MESSAGE))
                }
            }
        }

        fun unRegisterUser() {
            viewModelScope.launch {
                userRepository.unRegister().collectLatest { response ->
                    when (response) {
                        is ApiResponse.Success -> {
                            _event.emit(MyPageViewEvent.UnRegister)
                        }

                        is ApiResponse.Error -> {
                            _event.emit(MyPageViewEvent.Error(errorMessage = response.errorMessage))
                        }

                        is ApiResponse.Failure -> {}
                    }
                }
            }
        }
    }
