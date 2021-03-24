package com.example.talentsofttestwallyd.api

import okhttp3.OkHttpClient
import okhttp3.Request

class ApiClient {
    fun getClient(): OkHttpClient? {
        return OkHttpClient.Builder()
            .build()
    }

    fun getRequestBuilder(): Request.Builder {
        return Request.Builder()
    }
}