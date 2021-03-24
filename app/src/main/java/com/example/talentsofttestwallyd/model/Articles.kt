package com.example.talentsofttestwallyd.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.json.JSONObject

@Parcelize
class Articles(val json: String): Parcelable {
    private val jsonObject  = JSONObject(json)
    val author = jsonObject.optString("author")
    val title = jsonObject.optString("title")
    val description = jsonObject.optString("description")
    val content = jsonObject.optString("content")
    val imageUrl = jsonObject.optString("urlToImage")
    val publishedAt = jsonObject.optString("publishedAt")
    val source : Source = Source(jsonObject.optJSONObject("source"))

}
