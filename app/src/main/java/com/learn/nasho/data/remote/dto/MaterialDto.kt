package com.learn.nasho.data.remote.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MaterialDto(

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("embed")
	val embed: String? = null,

	@field:SerializedName("phase")
	val phase: Int? = null,

	@field:SerializedName("judul")
	val title: String? = null,

	@field:SerializedName("permalink")
	val permalink: String? = null,

	@field:SerializedName("file_uri")
	val fileUri: String? = null,

	@field:SerializedName("quis_status")
	val quizStatus: String? = null,

	@field:SerializedName("urutan")
	val order: Int? = null,

	@field:SerializedName("isi")
	val content: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

) : Parcelable
