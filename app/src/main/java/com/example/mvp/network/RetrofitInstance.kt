package com.example.mvp.network

import com.example.mvp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance
{
    companion object
    {
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val client = with(OkHttpClient.Builder())
            {
                addInterceptor(logging)
                build()
            }

            with(Retrofit.Builder())
            {
                baseUrl(BuildConfig.BASE_URL)
                addConverterFactory(GsonConverterFactory.create())
                client(client)
                build()
            }
        }

        val api by lazy {
            retrofit.create(NewsApi::class.java)
        }
    }
}