package com.mimo.android.data.model.response

data class LoginResponse(
    val userName: String? = null,
    val userContact: String? = null,
    val profileImageUrl: String? = null,
    val accessToken: String? = null,
    val refreshToken: String? = null,
)
