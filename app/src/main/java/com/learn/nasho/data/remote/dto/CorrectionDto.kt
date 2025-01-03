package com.learn.nasho.data.remote.dto

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class CorrectionDto(

    @field:SerializedName("incorrect_count")
    val incorrectCount: Int? = null,

    @field:SerializedName("score")
    val score: Int? = null,

    @field:SerializedName("correct_count")
    val correctCount: Int? = null,

    @field:SerializedName("total_score")
    val totalScore: Int? = null,

    @field:SerializedName("passed")
    val passed: Boolean? = null,

    @field:SerializedName("title")
    val title: String? = null
) : Parcelable
