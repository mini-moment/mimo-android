package com.mimo.android.data.repository

import com.mimo.android.data.request.UserRequest
import com.mimo.android.data.response.ApiResponse
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun signUp(userRequest: UserRequest): Flow<ApiResponse<Boolean>>
}
