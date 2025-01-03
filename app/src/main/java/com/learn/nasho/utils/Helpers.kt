package com.learn.nasho.utils

import android.content.Intent
import android.os.Build
import android.os.Parcelable
import android.view.View
import com.google.gson.Gson
import com.learn.nasho.data.remote.dto.ProfileDto

fun showLoading(loadingView: View) {
    loadingView.visibility = View.VISIBLE
}

fun hideLoading(loadingView: View) {
    loadingView.visibility = View.GONE
}

fun convertToJsonString(data: Any): String {
    val gson = Gson()
    return gson.toJson(data)
}

fun getDataProfileFromJson(data: String): ProfileDto {
    if (data.isNotEmpty()) {
        val gson = Gson()
        return gson.fromJson(data, ProfileDto::class.java)
    } else {
        return ProfileDto()
    }
}

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}