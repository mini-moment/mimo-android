package com.mimo.android.data.model.request

data class UserRequest(
    val userName: String = "",
    val userContact: String? = null,
    val accessToken: String = "",
    val refreshToken: String = "",
)
