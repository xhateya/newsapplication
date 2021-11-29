package com.xhateya.idn.newsapplication.service

import com.xhateya.idn.newsapplication.model.ResponseNews
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    fun getNewsHeadLines(
        @Query("country") country : String?,
        @Query("apikey") apikey : String?
    ):Call<ResponseNews>
}