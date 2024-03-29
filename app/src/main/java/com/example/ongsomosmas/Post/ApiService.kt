package com.example.ongsomosmas.Post

import com.example.ongsomosmas.Dto.*
import com.example.ongsomosmas.Dto.PostMessage
import com.example.ongsomosmas.Dto.Login
import com.example.ongsomosmas.Dto.News
import com.example.ongsomosmas.Dto.Register
import com.example.ongsomosmas.Dto.UserRegister

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("/api/register")
    fun registerUser(@Body register: Register): Call<RepositoryResponse<UserRegister>>

    @POST("/api/login")
    fun loginUser(@Body login: Login): Call<RepositoryResponse<UserRegister>>

    @GET("/api/news")
    fun getNews(@Query("limit") limit : Int): Call<RepositoryResponse<List<News>>>

    @GET("/api/members")
    fun getMembers(@Query("limit") limit : Int): Call<RepositoryResponse<List<Members>>>

    @POST("/api/contacts")
    fun postMessage(@Body post: PostMessage): Call<RepositoryResponse<PostMessage>>

}