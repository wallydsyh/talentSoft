package com.example.talentsofttestwallyd.api

import okhttp3.Call


interface ApiService {
     fun getAllNews(url: String): Call?
     fun getNewsImage(url: String?): Call?

}