package com.learn.nasho.data.locale.datastore

import kotlinx.coroutines.flow.Flow

interface DataStorePreferences {
    fun getUserTokenAccess(): Flow<String>
    suspend fun saveUserTokenAccess(token: String): Flow<Boolean>

    suspend fun clearTokenData(): Flow<Boolean>
    suspend fun setLogout(isLogout: Boolean): Flow<Boolean>
    fun getUserLogout(): Flow<Boolean>

    suspend fun saveUserProfileData(data: String): Flow<Boolean>
    fun getUserProfileData(): Flow<String>

    suspend fun setMaterial1ReadStep(step: Int): Flow<Boolean>
    fun getMaterial1ReadStep(): Flow<Int>

    suspend fun setMaterial2ReadStep(step: Int): Flow<Boolean>
    fun getMaterial2ReadStep(): Flow<Int>
}