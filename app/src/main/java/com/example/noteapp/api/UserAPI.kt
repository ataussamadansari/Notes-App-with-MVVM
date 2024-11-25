package com.example.noteapp.api

import com.example.noteapp.models.UserRequest
import com.example.noteapp.models.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserAPI {

    @POST("users/signup")
    suspend fun signup(@Body userRequest: UserRequest): Response<UserResponse>

    @POST("users/signin")
    suspend fun signin(@Body userRequest: UserRequest): Response<UserResponse>

    @GET("users/profile")
    suspend fun getUserProfile(): Response<UserResponse>
}