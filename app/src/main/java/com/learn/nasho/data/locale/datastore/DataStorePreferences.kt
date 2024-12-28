package com.learn.nasho.data.locale.datastore

import kotlinx.coroutines.flow.Flow

interface DataStorePreferences {
    fun getUserTokenAccess(): Flow<String>
    suspend fun saveUserTokenAccess(token: String): Flow<Boolean>

    suspend fun clearTokenData(): Flow<Boolean>

    suspend fun saveUserProfileData(data: String): Flow<Boolean>
    fun getUserProfileData(): Flow<String>
}