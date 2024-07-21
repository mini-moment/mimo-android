package com.mimo.domain.repository

import com.mimo.domain.model.ApiResponse
import com.mimo.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun login(user: User): Flow<ApiResponse<Boolean>>

    fun unRegister(): Flow<ApiResponse<Boolean>>
}
