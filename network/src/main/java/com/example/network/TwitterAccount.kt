package com.example.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TwitterAccount(val author: String, val url: String, val tweets: List<Tweet>)