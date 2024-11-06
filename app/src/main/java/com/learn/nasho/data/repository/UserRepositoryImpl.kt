package com.learn.nasho.data.repository

import com.google.gson.Gson
import com.learn.nasho.data.ResultState
import com.learn.nasho.data.locale.datastore.DataStorePreferences
import com.learn.nasho.data.remote.api.ApiService
import com.learn.nasho.data.remote.response.GeneralResponse
import com.learn.nasho.data.remote.response.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
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
                response.body()?.let {
                    if (it.error == true) {
                        emit(ResultState.Error(it.message ?: "Unknown error"))
                    } else {
                        emit(ResultState.Success(it))
                        val token = it.loginResult?.token
                        saveUserTokenAccess(token.toString()).collect {
                            setLogout(false).collect()
                        }
                    }
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
}