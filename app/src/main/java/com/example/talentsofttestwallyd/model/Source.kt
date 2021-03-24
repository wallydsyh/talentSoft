package com.example.talentsofttestwallyd.model

import org.json.JSONObject

class Source(jsonObject: JSONObject?) {
    val name = jsonObject?.optString("name")
}
