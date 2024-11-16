package com.learn.nasho.data.remote.api

import com.learn.nasho.data.remote.response.CategoriesResponse
import com.learn.nasho.data.remote.response.LoginResponse
import com.learn.nasho.data.remote.response.ProfileResponse
import com.learn.nasho.data.remote.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

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

//    @GET("v1/stories")
//    suspend fun getAllStories(
//        @Header("Authorization") token: String,
//        @Query("page") page: Int = 1,
//        @Query("size") size: Int = 20
//    ): Response<StoryListResponse>
//
//    @Multipart
//    @POST("v1/stories")
//    suspend fun uploadStory(
//        @Header("Authorization") token: String,
//        @Part file: MultipartBody.Part,
//        @Part("description") description: RequestBody,
//        @Part("lat") lat: RequestBody?,
//        @Part("lon") lon: RequestBody?,
//    ): Response<GeneralResponse>


}