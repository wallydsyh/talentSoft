package com.example.talentsofttestwallyd.api


class ApiHelper(private val apiService: ApiService) {
   fun getAllNews(url: String) = apiService.getAllNews(url)
   fun getNewsImage(url: String) = apiService.getNewsImage(url)
}