package com.example.talentsofttestwallyd.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.talentsofttestwallyd.repository.NewsRepository
import com.example.talentsofttestwallyd.utils.Resource
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    val allNews = MutableLiveData<Resource<String>>()
    val imageNews = MutableLiveData<Resource<ResponseBody>>()

    fun getAllNews(url: String) {
        newsRepository.getAllNews(url)?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                allNews.postValue(Resource.Error(e))
            }

            override fun onResponse(call: Call, response: Response) {
                allNews.postValue(Resource.Success(response.body()?.string().toString()))
                response.body()?.close()
            }
        })
    }

    fun getNewsImage(url: String) {
        newsRepository.getNewsImage(url)?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                imageNews.postValue(Resource.Error(e))
            }

            override fun onResponse(call: Call, response: Response) {
                response.body()?.let {
                    imageNews.postValue(Resource.Success(it))
                }
            }
        })
    }
}