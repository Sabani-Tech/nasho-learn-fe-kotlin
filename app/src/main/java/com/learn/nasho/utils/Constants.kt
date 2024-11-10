package com.learn.nasho.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {
    val USER_TOKEN = stringPreferencesKey("token_access")
    val USER_LOGOUT = booleanPreferencesKey("user_logout")
    val USER_PROFILE_DATA = stringPreferencesKey("data_user")
}