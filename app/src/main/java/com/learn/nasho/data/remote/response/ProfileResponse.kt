package com.learn.nasho.data.remote.response

import com.google.gson.annotations.SerializedName
import com.learn.nasho.data.remote.dto.ProfileDto

data class ProfileResponse(

//    @field:SerializedName("error")
//    val error: Boolean? = null,

    @field:SerializedName("statusCode")
    val statusCode: Int? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("data")
    val data: ProfileDto? = null
)