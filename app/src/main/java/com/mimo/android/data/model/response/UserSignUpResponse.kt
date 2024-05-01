package com.mimo.android.data.model.response

import com.mimo.android.util.ErrorCode

data class UserSignUpResponse(
    val data: Boolean? = null,
    val statusCode: ErrorCode? = null,
    val message: String? = null,
)
