package com.example.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Source(val hobbyId: Long, val url: String)