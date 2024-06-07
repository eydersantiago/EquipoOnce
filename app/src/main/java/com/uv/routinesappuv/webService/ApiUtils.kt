package com.uv.routinesappuv.webService

import com.uv.routinesappuv.utils.RetrofitClient

class ApiUtils {
    companion object {
        fun getApiService(): ApiService {
            return RetrofitClient.getRetrofit().create(ApiService::class.java)
        }

    }
}
