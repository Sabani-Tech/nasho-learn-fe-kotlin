package com.learn.nasho.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginDto(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("userId")
    val userId: String? = null,

    @field:SerializedName("token")
    val token: String? = null
)