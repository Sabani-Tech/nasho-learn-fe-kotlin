package com.learn.nasho.data.locale.datastore

import kotlinx.coroutines.flow.Flow

interface DataStorePreferences {
    fun getUserTokenAccess(): Flow<String>
    suspend fun saveUserTokenAccess(token: String): Flow<Boolean>

    suspend fun clearTokenData(): Flow<Boolean>
    suspend fun setLogout(isLogout: Boolean): Flow<Boolean>
    fun getUserLogout(): Flow<Boolean>
}