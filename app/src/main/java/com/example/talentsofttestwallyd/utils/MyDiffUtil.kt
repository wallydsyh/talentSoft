package com.example.talentsofttestwallyd.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.talentsofttestwallyd.model.NewsDataItem

class MyDiffUtil : DiffUtil.ItemCallback<NewsDataItem>() {
    override fun areItemsTheSame(oldItem: NewsDataItem, newItem: NewsDataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NewsDataItem, newItem: NewsDataItem): Boolean {
        return oldItem == newItem
    }

}
