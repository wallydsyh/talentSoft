package com.example.talentsofttestwallyd.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.example.talentsofttestwallyd.R
import com.example.talentsofttestwallyd.api.ApiHelper
import com.example.talentsofttestwallyd.api.ApiServiceImpl
import com.example.talentsofttestwallyd.databinding.ActivityMainBinding
import com.example.talentsofttestwallyd.model.Articles
import com.example.talentsofttestwallyd.viewModel.NewsViewModel
import com.example.talentsofttestwallyd.utils.ViewModelFactory

private const val newsApiURL =
    "https://newsapi.org/v2/top-headlines?country=fr&apiKey=fcc22664065548e6ba557a9a6fe29a05"

class MainActivity : AppCompatActivity() {

    private lateinit var newsViewModel: NewsViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            displayNewsFragment()
        }
        setUpViewModel()
        newsViewModel.getAllNews(newsApiURL)
    }

    private fun setUpViewModel() {
        newsViewModel = ViewModelProvider(this, ViewModelFactory(ApiHelper(ApiServiceImpl()))).get(
            NewsViewModel::class.java
        )
    }

    fun displayNewsDetailFragment(articles: Articles) {
        supportFragmentManager.commit {
            replace(R.id.fragment_container_view, NewsDetailFragment.newInstance(articles))
                .addToBackStack(NewsDetailFragment.toString())
        }
    }

    private fun displayNewsFragment() {
        supportFragmentManager.commit {
            add(R.id.fragment_container_view, NewsFragment())
        }
    }

}