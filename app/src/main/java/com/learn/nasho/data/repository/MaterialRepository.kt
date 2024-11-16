package com.learn.nasho.data.repository

import com.learn.nasho.data.ResultState
import com.learn.nasho.data.remote.response.CategoriesResponse
import kotlinx.coroutines.flow.Flow

interface MaterialRepository {
    suspend fun getCategoryList(): Flow<ResultState<CategoriesResponse>>
}