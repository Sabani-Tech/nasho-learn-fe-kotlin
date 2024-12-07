package com.learn.nasho.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {
    val USER_TOKEN = stringPreferencesKey("token_access")
    val USER_LOGOUT = booleanPreferencesKey("user_logout")
    val USER_PROFILE_DATA = stringPreferencesKey("data_user")
    val READ_MATERIAL1 = intPreferencesKey("material1_read_step")
    val READ_MATERIAL2 = intPreferencesKey("material2_read_step")

    const val CATEGORY_DATA = "data_category"
    const val MATERIAL_DATA = "data_material"
    const val MATERIAL_TYPE = "type_material"

    const val PLATFORM = "mobile"
    const val VERSION = "1.0"
    const val CLIENT_KEY = "c837d594-ed49-45a1-a3ea-667f4105352e"
}