package com.learn.nasho.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AnswerKeyDto(
    @field:SerializedName("key")
    val key: String? = null,
)
