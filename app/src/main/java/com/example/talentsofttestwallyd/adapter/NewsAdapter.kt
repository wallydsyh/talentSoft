package com.example.talentsofttestwallyd.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.talentsofttestwallyd.databinding.NewsItemBinding
import com.example.talentsofttestwallyd.model.Articles
import com.example.talentsofttestwallyd.model.NewsDataItem
import com.example.talentsofttestwallyd.utils.DateUtils
import com.example.talentsofttestwallyd.utils.MyDiffUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class NewsAdapter : ListAdapter<NewsDataItem, NewsAdapter.NewsViewHolder>(MyDiffUtil()) {
    private lateinit var binding: NewsItemBinding
    private val adapterScope = CoroutineScope(Dispatchers.Default)
    var onArticleClick: ((Articles) -> Unit)? = null

    class NewsViewHolder(var binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(newsDataItem: NewsDataItem.NewsDataModelItem) {
            binding.textViewDescription.text = newsDataItem.articles.description
            binding.textViewTitle.text = newsDataItem.articles.title
            binding.textviewPublishedAt.text =
                DateUtils().parseDate(newsDataItem.articles.publishedAt)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsDataItem: NewsDataItem.NewsDataModelItem =
            getItem(position) as NewsDataItem.NewsDataModelItem
        holder.bindData(newsDataItem)
        holder.itemView.setOnClickListener {
            onArticleClick?.invoke(newsDataItem.articles)
        }
    }

    fun addHeaderAndSubmitList(list: List<Articles>) {
        adapterScope.launch { // we do this in another thread in case the list is very long to compute
            val items = when {
                else -> list.map {
                    NewsDataItem.NewsDataModelItem(it)
                }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }
}



