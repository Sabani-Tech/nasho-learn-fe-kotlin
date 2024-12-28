package com.learn.nasho.utils

import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {
    val USER_TOKEN = stringPreferencesKey("token_access")
    val USER_PROFILE_DATA = stringPreferencesKey("data_user")

    const val NASHO_PREF_KEY = "nasho_preferences"
    const val CATEGORY_ID = "id_category"
    const val CATEGORY_DATA = "data_category"
    const val MATERIAL_ID = "id_material"
    const val MATERIAL_DATA = "data_material"
    const val MATERIAL_TYPE = "type_material"
    const val QUESTION_DATA = "data_question"
    const val CORRECTION_DATA = "data_correction"
    const val DISCUSSION_DATA = "data_discussion"
    const val QUESTION_TYPE = "type_question"
    const val EXAM_PHASE = "phase_exam"

    const val PLATFORM = "mobile"
    const val VERSION = "1.0"
    const val CLIENT_KEY = "d40056ba-fc84-4a19-9cea-50cf3beeb658"
}