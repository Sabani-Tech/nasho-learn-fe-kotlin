package com.learn.nasho.data.repository

import com.google.gson.Gson
import com.learn.nasho.data.ResultState
import com.learn.nasho.data.locale.datastore.DataStorePreferences
import com.learn.nasho.data.remote.api.ApiConfig
import com.learn.nasho.data.remote.api.ApiService
import com.learn.nasho.data.remote.response.GeneralResponse
import com.learn.nasho.data.remote.response.LoginResponse
import com.learn.nasho.data.remote.response.ProfileResponse
import com.learn.nasho.utils.convertToJsonString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(
    private val apiService: ApiService,
    private val dataStorePref: DataStorePreferences
) : UserRepository {

    override suspend fun registerUser(
        name: String,
        email: String,
        password: String
    ): Flow<ResultState<GeneralResponse>> = flow {
        emit(ResultState.Loading)
        try {
            val response = apiService.registerUser(name, email, password)
            if (response.isSuccessful) {
                response.body()?.let {
                    if (it.error == true) {
                        emit(ResultState.Error(it.message ?: "Unknown error"))
                    } else {
                        emit(ResultState.Success(it))
                    }
                } ?: run {
                    emit(ResultState.Error("Unknown error"))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = errorBody?.let {
                    Gson().fromJson(it, GeneralResponse::class.java)
                }
                emit(ResultState.Error(errorResponse?.message ?: "Unknown error"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ResultState.Error(e.message.toString()))
        }
    }

    override suspend fun loginUser(
        email: String,
        password: String
    ): Flow<ResultState<LoginResponse>> = flow {
        emit(ResultState.Loading)
        try {
            val response = apiService.loginUser(email, password)
            if (response.isSuccessful) {
                response.body()?.let { resp ->
//                    if (resp.error == true) {
//                        emit(ResultState.Error(resp.message ?: "Unknown error"))
//                    } else {
                        val token = resp.data?.token
                        saveUserTokenAccess(token.toString()).collect { isSaved ->
                            if (isSaved) {
                                setLogout(false).collect {
                                    emit(ResultState.Success(resp))
                                }
                            } else {
                                emit(ResultState.Error("Failed to save token"))
                            }
                        }
//                    }
                } ?: run {
                    emit(ResultState.Error("Unknown error"))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = errorBody?.let {
                    Gson().fromJson(it, LoginResponse::class.java)
                }
                emit(ResultState.Error(errorResponse?.message ?: "Unknown error"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ResultState.Error(e.message.toString()))
        }
    }

    override suspend fun getProfileUser(): Flow<ResultState<ProfileResponse>> = flow {
        emit(ResultState.Loading)
        try {
            val token = getTokenAccess().first()
            if (token.isBlank()) {
                emit(ResultState.Error("Token is empty, Re-Login"))
            } else {
                val platform = "mobile"
                val version = "1.0"
                val clientKey = "53a9cd0f-3902-4271-9fc4-7e53bc61bf34"
                val response =
                    apiService.getProfileUser(
                        ApiConfig.getAuthHeader(token),
                        platform,
                        version,
                        clientKey
                    )
                if (response.isSuccessful) {
                    response.body()?.let { data ->
                        saveUserProfileData(convertToJsonString(data)).collect { isSaved ->
                            if (isSaved) {
                                emit(ResultState.Success(data))
                            } else {
                                emit(ResultState.Error("Failed to save profile data"))
                            }
                        }
                    } ?: run {
                        clearTokenData().collect()
                        emit(ResultState.Error("Unknown error"))
                    }
                } else {
                    clearTokenData().collect()
                    val errorBody = response.errorBody()?.string()
                    val errorResponse = errorBody?.let {
                        Gson().fromJson(it, GeneralResponse::class.java)
                    }
                    emit(ResultState.Error(errorResponse?.message ?: "Unknown error"))
                }
            }
        } catch (e: Exception) {
            clearTokenData().collect()
            e.printStackTrace()
            emit(ResultState.Error(e.message.toString()))
        }
    }

    override fun getTokenAccess(): Flow<String> {
        return dataStorePref.getUserTokenAccess()
    }

    override suspend fun clearTokenData(): Flow<Boolean> {
        return dataStorePref.clearTokenData()
    }

    override suspend fun setLogout(isLogout: Boolean): Flow<Boolean> {
        return dataStorePref.setLogout(isLogout)
    }


    private suspend fun saveUserTokenAccess(token: String): Flow<Boolean> {
        return dataStorePref.saveUserTokenAccess(token)
    }

    override fun getProfileUserData(): Flow<String> {
        return dataStorePref.getUserProfileData()
    }

    private suspend fun saveUserProfileData(data: String): Flow<Boolean> {
        return dataStorePref.saveUserProfileData(data)
    }
}