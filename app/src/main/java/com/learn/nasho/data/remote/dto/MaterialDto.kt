package com.learn.nasho.data.remote.dto

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class MaterialDto(

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("embed")
	val embed: String? = null,

	@field:SerializedName("judul")
	val title: String? = null,

	@field:SerializedName("permalink")
	val permalink: String? = null,

	@field:SerializedName("isi")
	val content: String? = null,

	@field:SerializedName("kategori_materi_id")
	val categoryMaterialId: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

) : Parcelable
