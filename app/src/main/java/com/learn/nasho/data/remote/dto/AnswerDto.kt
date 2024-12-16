package com.learn.nasho.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AnswerDto(
    @field:SerializedName("quis_id")
    val id: String? = null,

    @field:SerializedName("point")
    val point: Int? = null,

    @field:SerializedName("batch")
    val batch: Int? = null,

    @field:SerializedName("answer")
    val answer: AnswerKey? = null,
)

data class AnswerKey(
    @field:SerializedName("key")
    val key: String? = null,
)
