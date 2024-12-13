package com.learn.nasho.data.remote.dto

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class QuestionDto(

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("question")
    val question: String? = null,

    @field:SerializedName("point")
    val point: Int? = null,

    @field:SerializedName("option")
    val option: List<Option>? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null

) : Parcelable

@Parcelize
data class Option(
    @field:SerializedName("key")
    val key: String? = null,

    @field:SerializedName("value")
    val value: String? = null,
) : Parcelable
