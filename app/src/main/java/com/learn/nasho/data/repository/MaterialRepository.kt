package com.learn.nasho.data.repository

import com.learn.nasho.data.ResultState
import com.learn.nasho.data.remote.response.CategoriesResponse
import com.learn.nasho.data.remote.response.CategoryDetailResponse
import com.learn.nasho.data.remote.response.GeneralResponse
import com.learn.nasho.data.remote.response.MaterialsResponse
import com.learn.nasho.data.remote.response.QuestionListResponse
import kotlinx.coroutines.flow.Flow

interface MaterialRepository {
    suspend fun getCategoryList(): Flow<ResultState<CategoriesResponse>>
    suspend fun getCategoryDetailById(categoryId: String): Flow<ResultState<CategoryDetailResponse>>
    suspend fun getMaterialListByCategory(categoryId: String): Flow<ResultState<MaterialsResponse>>
    suspend fun updateStatus(categoryId: String, status:String): Flow<ResultState<GeneralResponse>>
    suspend fun getExamQuestions(categoryId: String, phase: Int): Flow<ResultState<QuestionListResponse>>
    suspend fun getQuizQuestions(categoryId: String, materialId: String): Flow<ResultState<QuestionListResponse>>

    suspend fun setMaterialReadStep(materialNumber: Int, step: Int): Flow<Boolean>
    fun getMaterialReadStep(materialNumber: Int): Flow<Int>
}