package com.mimo.android.data.request

data class UserRequest(
    val userName: String = "",
    val userContract: String = "",
    val accessToken: String = "",
    val refreshToken: String = "",
)
