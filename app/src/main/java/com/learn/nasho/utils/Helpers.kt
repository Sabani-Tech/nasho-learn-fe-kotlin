package com.learn.nasho.utils

import android.view.View
import com.google.gson.Gson

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