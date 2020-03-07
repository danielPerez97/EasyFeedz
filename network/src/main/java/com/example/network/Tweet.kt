package com.example.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Tweet( val tweet: String, val url: String )