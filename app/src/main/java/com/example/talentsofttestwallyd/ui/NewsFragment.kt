package com.example.talentsofttestwallyd.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.talentsofttestwallyd.adapter.NewsAdapter
import com.example.talentsofttestwallyd.databinding.NewsFragmentBinding
import com.example.talentsofttestwallyd.model.Articles
import com.example.talentsofttestwallyd.viewModel.NewsViewModel
import com.example.talentsofttestwallyd.utils.Resource
import org.json.JSONObject


class NewsFragment : Fragment() {

    private val newsViewModel: NewsViewModel by activityViewModels()
    private lateinit var binding: NewsFragmentBinding
    private val newsAdapter = NewsAdapter()
    private lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NewsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpListener()
        setUpObserver()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setUpListener() {
        binding.recycleView.apply {
            layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
            adapter = newsAdapter
            newsAdapter.onArticleClick = {
                mainActivity.displayNewsDetailFragment(it)
                newsViewModel.getNewsImage(it.imageUrl)
            }
        }

    }

    private fun setUpObserver() {
        newsViewModel.allNews.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    val json = JSONObject(response.data)
                    val listOfArticles = parseJsonResponse(json)
                    listOfArticles?.let {
                        newsAdapter.addHeaderAndSubmitList(it)
                    }
                }
                is Resource.Error -> {
                    Log.e(NewsDetailFragment().javaClass.simpleName, response.exception.localizedMessage)
                }
            }

        })
    }

    private fun parseJsonResponse(jsonObject: JSONObject): List<Articles>? {
        return jsonObject.optJSONArray("articles")?.let { jsonArray ->
            0.until(jsonArray.length()).map { i -> jsonArray.optJSONObject(i) }
        }?.map { Articles(it.toString()) }
    }
}