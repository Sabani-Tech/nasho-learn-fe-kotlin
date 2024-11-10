package com.learn.nasho.data.repository

import com.learn.nasho.data.ResultState
import com.learn.nasho.data.remote.response.GeneralResponse
import com.learn.nasho.data.remote.response.LoginResponse
import com.learn.nasho.data.remote.response.ProfileResponse
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun registerUser(
        name: String,
        email: String,
        password: String
    ): Flow<ResultState<GeneralResponse>>

    suspend fun loginUser(
        email: String,
        password: String
    ): Flow<ResultState<LoginResponse>>

    suspend fun getProfileUser(): Flow<ResultState<ProfileResponse>>

    fun getTokenAccess(): Flow<String>

    suspend fun clearTokenData(): Flow<Boolean>
    suspend fun setLogout(isLogout : Boolean): Flow<Boolean>
}