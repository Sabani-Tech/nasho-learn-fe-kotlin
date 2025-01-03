package com.learn.nasho.data.repository

import com.learn.nasho.data.ResultState
import com.learn.nasho.data.remote.dto.AnswerExamDto
import com.learn.nasho.data.remote.dto.AnswerQuizDto
import com.learn.nasho.data.remote.response.CategoriesResponse
import com.learn.nasho.data.remote.response.CategoryDetailResponse
import com.learn.nasho.data.remote.response.CorrectionResponse
import com.learn.nasho.data.remote.response.MaterialDetailResponse
import com.learn.nasho.data.remote.response.QuestionListResponse
import com.learn.nasho.data.remote.response.QuizDiscussionResponse
import kotlinx.coroutines.flow.Flow

interface MaterialRepository {
    suspend fun getCategoryList(): Flow<ResultState<CategoriesResponse>>
    suspend fun getCategoryDetailById(categoryId: String): Flow<ResultState<CategoryDetailResponse>>
    suspend fun getMaterialDetail(materialId: String): Flow<ResultState<MaterialDetailResponse>>
    suspend fun getExamQuestions(categoryId: String, phase: Int): Flow<ResultState<QuestionListResponse>>
    suspend fun getQuizQuestions(categoryId: String, materialId: String): Flow<ResultState<QuestionListResponse>>
    suspend fun submitExam(categoryId: String, phase: Int, exam: List<AnswerExamDto>): Flow<ResultState<CorrectionResponse>>
    suspend fun submitQuiz(categoryId: String, materialId: String, quiz: List<AnswerQuizDto>): Flow<ResultState<CorrectionResponse>>
    suspend fun getExamDiscussion(categoryId: String, phase: Int): Flow<ResultState<QuizDiscussionResponse>>
    suspend fun getQuizDiscussion(categoryId: String, materialId: String): Flow<ResultState<QuizDiscussionResponse>>
}