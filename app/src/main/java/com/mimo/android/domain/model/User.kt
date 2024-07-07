package com.mimo.android.domain.model

data class User(
    val userName: String? = null,
    val userContact: String? = null,
    val profileImageUrl: String? = null,
    val accessToken: String? = null,
    val refreshToken: String? = null,
)
