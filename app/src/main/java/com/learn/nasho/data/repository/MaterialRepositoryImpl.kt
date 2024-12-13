package com.learn.nasho.data.repository

import com.google.gson.Gson
import com.learn.nasho.data.ResultState
import com.learn.nasho.data.locale.datastore.DataStorePreferences
import com.learn.nasho.data.remote.api.ApiConfig
import com.learn.nasho.data.remote.api.ApiService
import com.learn.nasho.data.remote.response.CategoriesResponse
import com.learn.nasho.data.remote.response.CategoryDetailResponse
import com.learn.nasho.data.remote.response.GeneralResponse
import com.learn.nasho.data.remote.response.MaterialsResponse
import com.learn.nasho.data.remote.response.QuestionListResponse
import com.learn.nasho.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class MaterialRepositoryImpl(
    private val apiService: ApiService,
    private val dataStorePref: DataStorePreferences
) : MaterialRepository {

    override suspend fun getCategoryList(): Flow<ResultState<CategoriesResponse>> = flow {
        emit(ResultState.Loading)
        try {
            val token = getTokenAccess().first()
            if (token.isBlank()) {
                emit(ResultState.Error("Token is empty, Re-Login"))
            } else {
                val response =
                    apiService.getCategoryList(
                        ApiConfig.getAuthHeader(token),
                        Constants.PLATFORM,
                        Constants.VERSION,
                        Constants.CLIENT_KEY
                    )
                if (response.isSuccessful) {
                    response.body()?.let { data ->
                        if (data.error == true) {
                            emit(ResultState.Error(data.message ?: "Unknown error"))
                        } else {
                            emit(ResultState.Success(data))
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
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ResultState.Error(e.message.toString()))
        }
    }

    override suspend fun getCategoryDetailById(categoryId: String): Flow<ResultState<CategoryDetailResponse>> =
        flow {
            emit(ResultState.Loading)
            try {
                val token = getTokenAccess().first()
                if (token.isBlank()) {
                    emit(ResultState.Error("Token is empty, Re-Login"))
                } else {
                    val response =
                        apiService.getCategoryDetailById(
                            ApiConfig.getAuthHeader(token),
                            Constants.PLATFORM,
                            Constants.VERSION,
                            Constants.CLIENT_KEY,
                            categoryId
                        )
                    if (response.isSuccessful) {
                        response.body()?.let { data ->
                            if (data.error == true) {
                                emit(ResultState.Error(data.message ?: "Unknown error"))
                            } else {
                                emit(ResultState.Success(data))
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
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ResultState.Error(e.message.toString()))
            }
        }

    override suspend fun getMaterialListByCategory(categoryId: String): Flow<ResultState<MaterialsResponse>> =
        flow {
            emit(ResultState.Loading)
            try {
                val token = getTokenAccess().first()
                if (token.isBlank()) {
                    emit(ResultState.Error("Token is empty, Re-Login"))
                } else {
                    val response =
                        apiService.getMaterialListByCategory(
                            ApiConfig.getAuthHeader(token),
                            Constants.PLATFORM,
                            Constants.VERSION,
                            Constants.CLIENT_KEY,
                            categoryId
                        )
                    if (response.isSuccessful) {
                        response.body()?.let { data ->
                            if (data.error == true) {
                                emit(ResultState.Error(data.message ?: "Unknown error"))
                            } else {
                                emit(ResultState.Success(data))
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
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ResultState.Error(e.message.toString()))
            }
        }

    override suspend fun updateStatus(
        categoryId: String,
        status: String
    ): Flow<ResultState<GeneralResponse>> =
        flow {
            emit(ResultState.Loading)
            try {
                val token = getTokenAccess().first()
                if (token.isBlank()) {
                    emit(ResultState.Error("Token is empty, Re-Login"))
                } else {
                    val response =
                        apiService.updateStatus(
                            ApiConfig.getAuthHeader(token),
                            Constants.PLATFORM,
                            Constants.VERSION,
                            Constants.CLIENT_KEY,
                            categoryId,
                            status
                        )
                    if (response.isSuccessful) {
                        response.body()?.let { data ->
                            if (data.error == true) {
                                emit(ResultState.Error(data.message ?: "Unknown error"))
                            } else {
                                emit(ResultState.Success(data))
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
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ResultState.Error(e.message.toString()))
            }
        }

    override suspend fun getExamQuestions(
        categoryId: String, phase: Int
    ): Flow<ResultState<QuestionListResponse>> =
        flow {
            emit(ResultState.Loading)
            try {
                val token = getTokenAccess().first()
                if (token.isBlank()) {
                    emit(ResultState.Error("Token is empty, Re-Login"))
                } else {
                    val response =
                        apiService.getExamQuestions(
                            ApiConfig.getAuthHeader(token),
                            Constants.PLATFORM,
                            Constants.VERSION,
                            Constants.CLIENT_KEY,
                            categoryId, phase
                        )
                    if (response.isSuccessful) {
                        response.body()?.let { data ->
                            if (data.error == true) {
                                emit(ResultState.Error(data.message ?: "Unknown error"))
                            } else {
                                emit(ResultState.Success(data))
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
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ResultState.Error(e.message.toString()))
            }
        }

    override suspend fun getQuizQuestions(
        categoryId: String, materialId: String
    ): Flow<ResultState<QuestionListResponse>> =
        flow {
            emit(ResultState.Loading)
            try {
                val token = getTokenAccess().first()
                if (token.isBlank()) {
                    emit(ResultState.Error("Token is empty, Re-Login"))
                } else {
                    val response =
                        apiService.getQuizQuestions(
                            ApiConfig.getAuthHeader(token),
                            Constants.PLATFORM,
                            Constants.VERSION,
                            Constants.CLIENT_KEY,
                            categoryId, materialId
                        )
                    if (response.isSuccessful) {
                        response.body()?.let { data ->
                            if (data.error == true) {
                                emit(ResultState.Error(data.message ?: "Unknown error"))
                            } else {
                                emit(ResultState.Success(data))
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
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ResultState.Error(e.message.toString()))
            }
        }

    override suspend fun setMaterialReadStep(materialNumber: Int, step: Int): Flow<Boolean> {
        return if (materialNumber == 1) {
            dataStorePref.setMaterial1ReadStep(step = step)
        } else {
            dataStorePref.setMaterial2ReadStep(step = step)
        }
    }

    override fun getMaterialReadStep(materialNumber: Int): Flow<Int> {
        return if (materialNumber == 1) {
            dataStorePref.getMaterial1ReadStep()
        } else {
            dataStorePref.getMaterial2ReadStep()
        }
    }


    private fun getTokenAccess(): Flow<String> {
        return dataStorePref.getUserTokenAccess()
    }
}