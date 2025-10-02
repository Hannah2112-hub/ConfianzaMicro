package com.example.confianzamicro

import com.example.confianzamicro.Post
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("posts/1")
    fun getPost(): Call<Post>
}