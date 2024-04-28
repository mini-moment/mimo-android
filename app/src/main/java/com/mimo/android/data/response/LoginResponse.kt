package com.mimo.android.data.response

data class LoginResponse(
    val userName: String = "",
    val userContract: String = "",
    val accessToken: String = "",
    val refreshToken: String = "",
)
