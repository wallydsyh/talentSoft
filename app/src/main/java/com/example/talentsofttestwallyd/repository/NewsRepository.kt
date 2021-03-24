package com.example.talentsofttestwallyd.repository

import com.example.talentsofttestwallyd.api.ApiHelper
import okhttp3.Call

class NewsRepository(private val apiHelper: ApiHelper) {
    fun getAllNews(url: String): Call? {
        return apiHelper.getAllNews(url)
    }

    fun getNewsImage(url: String): Call? {
        return apiHelper.getNewsImage(url)
    }

}