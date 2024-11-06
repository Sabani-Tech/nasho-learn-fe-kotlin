package com.learn.nasho.utils

import android.view.View

fun showLoading(loadingView: View) {
    loadingView.visibility = View.VISIBLE
}

fun hideLoading(loadingView: View) {
    loadingView.visibility = View.GONE
}