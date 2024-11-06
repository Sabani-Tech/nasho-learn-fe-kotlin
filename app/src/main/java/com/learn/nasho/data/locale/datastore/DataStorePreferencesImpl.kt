package com.learn.nasho.data.locale.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.learn.nasho.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "story_preferences")

class DataStorePreferencesImpl(private val dataStore: DataStore<Preferences>) :
    DataStorePreferences {

    override fun getUserTokenAccess(): Flow<String> {
        return dataStore.data.map {
            it[Constants.USER_TOKEN] ?: ""
        }
    }

    override suspend fun saveUserTokenAccess(token: String): Flow<Boolean> = flow {
        dataStore.edit {
            it[Constants.USER_TOKEN] = token
        }
        emit(true)
    }.catch { emit(false) }

    override suspend fun clearTokenData(): Flow<Boolean> = flow {
        dataStore.edit {
            it.remove(Constants.USER_TOKEN)
        }
        emit(true)
    }.catch {
        emit(false)
    }

    override suspend fun setLogout(isLogout: Boolean): Flow<Boolean> = flow {
        dataStore.edit {
            it[Constants.USER_LOGOUT] = isLogout
        }
        emit(true)
    }.catch {
        emit(false)
    }

    override fun getUserLogout(): Flow<Boolean> {
        return dataStore.data.map {
            it[Constants.USER_LOGOUT] ?: false
        }
    }
}