package com.example.talentsofttestwallyd.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.talentsofttestwallyd.api.ApiHelper
import com.example.talentsofttestwallyd.viewModel.NewsViewModel
import com.example.talentsofttestwallyd.repository.NewsRepository

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(NewsViewModel::class.java) -> {
                return NewsViewModel(NewsRepository(apiHelper)) as T
            }
            else -> throw IllegalArgumentException("Unknown class name")
        }
    }

}