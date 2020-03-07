package com.example.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Hobby(val id: Long, val name: String)