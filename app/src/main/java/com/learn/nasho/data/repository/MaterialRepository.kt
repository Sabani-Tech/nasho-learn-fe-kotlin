package com.learn.nasho.data.repository

import com.learn.nasho.data.ResultState
import com.learn.nasho.data.remote.response.CategoriesResponse
import com.learn.nasho.data.remote.response.MaterialsResponse
import kotlinx.coroutines.flow.Flow

interface MaterialRepository {
    suspend fun getCategoryList(): Flow<ResultState<CategoriesResponse>>
    suspend fun getMaterialListByCategory(categoryId: String): Flow<ResultState<MaterialsResponse>>
}