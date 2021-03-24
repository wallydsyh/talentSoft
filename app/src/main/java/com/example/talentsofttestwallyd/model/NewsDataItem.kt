package com.example.talentsofttestwallyd.model

sealed class NewsDataItem {
    abstract val id: String

    data class NewsDataModelItem(val articles: Articles) : NewsDataItem() {
        override val id: String = Long.MIN_VALUE.toString()
    }
}
