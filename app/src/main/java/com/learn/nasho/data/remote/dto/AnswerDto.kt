package com.learn.nasho.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AnswerDto(
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("question")
    val question: String? = null,

    @field:SerializedName("point")
    val point: Int? = null,

    @field:SerializedName("answer")
    val answer: Option? = null,
)
