package com.example.talentsofttestwallyd.api

import android.util.Log
import okhttp3.Call
import okhttp3.Request
import java.net.MalformedURLException

class ApiServiceImpl(
    private val request: Request.Builder = ApiClient().getRequestBuilder()
) : ApiService {
    override fun getAllNews(url: String): Call? {
        request.url(url)
        return ApiClient().getClient()?.newCall(request.build())
    }

    override fun getNewsImage(url: String?): Call? {
        var call: Call? = null
        try {
            request.url(url.toString())
            call = ApiClient().getClient()?.newCall(request.build())
        } catch (e: MalformedURLException) {
            Log.e(ApiServiceImpl().javaClass.simpleName, e.localizedMessage)
        } catch (e: IllegalArgumentException) {
            Log.e(ApiServiceImpl().javaClass.simpleName, e.localizedMessage)
        }
        return call
    }
}