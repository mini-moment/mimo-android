package com.mimo.android.presentation.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mimo.android.data.model.response.ApiResponse
import com.mimo.android.data.repository.PostRepository
import com.mimo.android.domain.model.PostData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {

    private val _myPostList = MutableLiveData<List<PostData>>()
    val myPostList: LiveData<List<PostData>> get() = _myPostList

    fun setMyPostList(value: List<PostData>) {
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
}