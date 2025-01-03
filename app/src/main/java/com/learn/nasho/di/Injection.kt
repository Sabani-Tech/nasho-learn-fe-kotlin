package com.learn.nasho.di

import android.content.Context
import com.learn.nasho.data.locale.datastore.DataStorePreferences
import com.learn.nasho.data.locale.datastore.DataStorePreferencesImpl
import com.learn.nasho.data.locale.datastore.dataStore
import com.learn.nasho.data.remote.api.ApiConfig
import com.learn.nasho.data.remote.api.ApiService
import com.learn.nasho.data.repository.MaterialRepository
import com.learn.nasho.data.repository.MaterialRepositoryImpl
import com.learn.nasho.data.repository.UserRepository
import com.learn.nasho.data.repository.UserRepositoryImpl

object Injection {
    private fun provideApiService(): ApiService = ApiConfig.getApiService()

    private fun provideDataStorePref(context: Context): DataStorePreferences =
        DataStorePreferencesImpl(context.dataStore)

    fun provideUserRepository(context: Context): UserRepository {
        val apiService = provideApiService()
        val dataStorePreferences = provideDataStorePref(context)
        return UserRepositoryImpl(apiService, dataStorePreferences)
    }

    fun provideMaterialRepository(context: Context): MaterialRepository {
        val apiService = provideApiService()
        val dataStorePreferences = provideDataStorePref(context)
        return MaterialRepositoryImpl(apiService, dataStorePreferences)
    }
}
