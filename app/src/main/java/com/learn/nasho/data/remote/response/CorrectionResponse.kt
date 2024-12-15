package com.learn.nasho.data.remote.response

import com.google.gson.annotations.SerializedName
import com.learn.nasho.data.remote.dto.CategoryDto
import com.learn.nasho.data.remote.dto.CorrectionDto

data class CorrectionResponse(

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("statusCode")
    val statusCode: Int? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("data")
    val data: CorrectionDto? = null
)