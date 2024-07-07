package com.mimo.android.domain.repository

import com.mimo.android.domain.model.ApiResponse
import com.mimo.android.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun signUp(user: User): Flow<ApiResponse<Boolean>>
}
