package com.learn.nasho.data.remote.dto

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class QuizDiscussionDto(

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("question")
    val question: String? = null,

    @field:SerializedName("point")
    val point: Int? = null,

    @field:SerializedName("answer")
    val answer: Option? = null,

    @field:SerializedName("right_answer")
    val rightAnswer: Option? = null,

) : Parcelable
