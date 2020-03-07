package com.example.easyfeedz.model

sealed class ViewFeed
{
    data class SimpleUrlFeed(val url: String): ViewFeed()
    data class TwitterFeed(val name: String, val username : String, val tweetText : String): ViewFeed()
}