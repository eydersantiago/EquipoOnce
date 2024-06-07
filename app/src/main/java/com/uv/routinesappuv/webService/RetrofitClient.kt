package com.uv.routinesappuv.utils

import okhttp3.Interceptor
import okhttp3.Request
import com.uv.routinesappuv.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient

object RetrofitClient {

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original: Request = chain.request()
                val requestBuilder: Request.Builder = original.newBuilder()
                    .header("limit", "0") // Cambia el límite a 0
                    .header("X-RapidAPI-Key", Constants.API_KEY)
                    .header("X-RapidAPI-Host", Constants.API_HOST)

                val request: Request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()
    }

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
