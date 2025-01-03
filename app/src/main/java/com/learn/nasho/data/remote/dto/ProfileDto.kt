package com.learn.nasho.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ProfileDto(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("nama_lengkap")
    val fullName: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("role_id")
    val roleId: Int? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,
)
