package com.learn.nasho.data.remote.api

import com.learn.nasho.data.remote.dto.AnswerExamDto
import com.learn.nasho.data.remote.dto.AnswerQuizDto
import com.learn.nasho.data.remote.response.CategoriesResponse
import com.learn.nasho.data.remote.response.CategoryDetailResponse
import com.learn.nasho.data.remote.response.CorrectionResponse
import com.learn.nasho.data.remote.response.LoginResponse
import com.learn.nasho.data.remote.response.MaterialDetailResponse
import com.learn.nasho.data.remote.response.ProfileResponse
import com.learn.nasho.data.remote.response.QuestionListResponse
import com.learn.nasho.data.remote.response.QuizDiscussionResponse
import com.learn.nasho.data.remote.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @FormUrlEncoded
    @POST("v1/user/auth/register")
    suspend fun registerUser(
        @Field("nama_lengkap") fullName: String,
        @Field("umail") email: String,
        @Field("password") password: String,
        @Field("password_confirmation") passwordConfirmation: String,
    ): Response<RegisterResponse>

    @FormUrlEncoded
    @POST("v1/user/auth/login")
    suspend fun loginUser(
        @Field("umail") email: String,
        @Field("password") password: String
    ): Response<LoginResponse>

    @GET("v1/user/profile")
    suspend fun getProfileUser(
        @Header("Authorization") token: String,
        @Header("X-PLATFORM-NASHO") platform: String,
        @Header("X-VERSION-NASHO") version: String,
        @Header("X-CLIENT-KEY-NASHO") clientKey: String
    ): Response<ProfileResponse>

    @GET("v1/user/category")
    suspend fun getCategoryList(
        @Header("Authorization") token: String,
        @Header("X-PLATFORM-NASHO") platform: String,
        @Header("X-VERSION-NASHO") version: String,
        @Header("X-CLIENT-KEY-NASHO") clientKey: String
    ): Response<CategoriesResponse>

    @GET("v1/user/category/{id}/materi")
    suspend fun getCategoryDetailById(
        @Header("Authorization") token: String,
        @Header("X-PLATFORM-NASHO") platform: String,
        @Header("X-VERSION-NASHO") version: String,
        @Header("X-CLIENT-KEY-NASHO") clientKey: String,
        @Path("id") categoryId: String
    ): Response<CategoryDetailResponse>

    @GET("v1/user/materi/{materi_id}")
    suspend fun getMaterialDetails(
        @Header("Authorization") token: String,
        @Header("X-PLATFORM-NASHO") platform: String,
        @Header("X-VERSION-NASHO") version: String,
        @Header("X-CLIENT-KEY-NASHO") clientKey: String,
        @Path("materi_id") materialID: String
    ): Response<MaterialDetailResponse>

    @GET("v1/user/category/{category_id}/exam")
    suspend fun getExamQuestions(
        @Header("Authorization") token: String,
        @Header("X-PLATFORM-NASHO") platform: String,
        @Header("X-VERSION-NASHO") version: String,
        @Header("X-CLIENT-KEY-NASHO") clientKey: String,
        @Path("category_id") categoryId: String,
        @Query("phase") phase: Int
    ): Response<QuestionListResponse>

    @GET("v1/user/category/{category_id}/materi/{materi_id}/quis")
    suspend fun getQuizQuestions(
        @Header("Authorization") token: String,
        @Header("X-PLATFORM-NASHO") platform: String,
        @Header("X-VERSION-NASHO") version: String,
        @Header("X-CLIENT-KEY-NASHO") clientKey: String,
        @Path("category_id") categoryId: String,
        @Path("materi_id") materialId: String
    ): Response<QuestionListResponse>

    @POST("v1/user/category/{category_id}/materi/{materi_id}/quis/submit")
    suspend fun submitQuiz(
        @Header("Authorization") token: String,
        @Header("X-PLATFORM-NASHO") platform: String,
        @Header("X-VERSION-NASHO") version: String,
        @Header("X-CLIENT-KEY-NASHO") clientKey: String,
        @Path("category_id") categoryId: String,
        @Path("materi_id") materialId: String,
        @Body quiz: List<AnswerQuizDto>
    ): Response<CorrectionResponse>

    @POST("v1/user/category/{category_id}/exam/submit")
    suspend fun submitExam(
        @Header("Authorization") token: String,
        @Header("X-PLATFORM-NASHO") platform: String,
        @Header("X-VERSION-NASHO") version: String,
        @Header("X-CLIENT-KEY-NASHO") clientKey: String,
        @Path("category_id") categoryId: String,
        @Query("phase") phase: Int,
        @Body exam: List<AnswerExamDto>
    ): Response<CorrectionResponse>

    @GET("v1/user/category/{category_id}/exam/result")
    suspend fun getExamDiscussion(
        @Header("Authorization") token: String,
        @Header("X-PLATFORM-NASHO") platform: String,
        @Header("X-VERSION-NASHO") version: String,
        @Header("X-CLIENT-KEY-NASHO") clientKey: String,
        @Path("category_id") categoryId: String,
        @Query("phase") phase: Int
    ): Response<QuizDiscussionResponse>

    @GET("v1/user/category/{category_id}/materi/{materi_id}/quis/result")
    suspend fun getQuizDiscussion(
        @Header("Authorization") token: String,
        @Header("X-PLATFORM-NASHO") platform: String,
        @Header("X-VERSION-NASHO") version: String,
        @Header("X-CLIENT-KEY-NASHO") clientKey: String,
        @Path("category_id") categoryId: String,
        @Path("materi_id") materialId: String
    ): Response<QuizDiscussionResponse>
}