package com.mimo.android.data.repository

import com.mimo.android.data.model.request.UserRequest
import com.mimo.android.data.model.response.ApiResponse
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun signUp(userRequest: UserRequest): Flow<ApiResponse<Boolean>>
}
