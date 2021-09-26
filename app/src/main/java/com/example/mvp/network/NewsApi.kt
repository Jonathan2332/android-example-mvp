package com.example.mvp.network

import com.example.mvp.BuildConfig
import com.example.mvp.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi
{
    @GET("/v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country") countryCode: String = "br",
        @Query("page") pageNumber: Int = 1,
        @Query("apiKey") apiKey:String = BuildConfig.API_KEY
    ) : Response<NewsResponse>

    @GET("/v2/everything")
    suspend fun searchNews(
        @Query("q") searchQuery: String,
        @Query("page") pageNumber:Int = 1,
        @Query("apiKey") apiKey:String = BuildConfig.API_KEY
    ) : Response<NewsResponse>
}