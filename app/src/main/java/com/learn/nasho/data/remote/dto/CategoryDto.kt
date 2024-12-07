package com.learn.nasho.data.remote.dto

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class CategoryDto(

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("deskripsi")
	val desc: String? = null,

	@field:SerializedName("jenis")
	val type: String? = null,

	@field:SerializedName("jenis_arab")
	val typeArab: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("materi_phase1")
	val materialPhase1: List<MaterialDto>? = null,

	@field:SerializedName("materi_phase2")
	val materialPhase2: List<MaterialDto>? = null,
) : Parcelable
