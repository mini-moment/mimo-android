package com.mimo.domain.repository

import com.mimo.domain.model.ApiResponse
import com.mimo.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun signUp(user: User): Flow<ApiResponse<Boolean>>
}
