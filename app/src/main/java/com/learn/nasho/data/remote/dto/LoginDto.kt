package com.learn.nasho.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginDto(

    @field:SerializedName("token")
    val token: String? = null,

    @field:SerializedName("expired_at")
    val expiredAt: String? = null,
)