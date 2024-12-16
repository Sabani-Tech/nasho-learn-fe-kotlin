package com.learn.nasho.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.learn.nasho.data.remote.dto.QuizDiscussionDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuizDiscussionResponse(

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("statusCode")
    val statusCode: Int? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("data")
    val data: List<QuizDiscussionDto>? = null
) : Parcelable