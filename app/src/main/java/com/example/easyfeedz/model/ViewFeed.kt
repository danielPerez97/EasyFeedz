package com.example.easyfeedz.model

sealed class ViewFeed
{
    data class SimpleUrlFeed(val name: String, val url: String): ViewFeed()
    data class TwitterFeed(val name: String, val username : String, val tweetText : String, val url: String, val imageUrl: String? = null): ViewFeed()
    data class YoutubeFeed(val channel: String, val title: String, val url : String, val thumbUrl : String, val description: String): ViewFeed()
    data class TwitchFeed(val channel : String, val url : String, val description: String, val imageUrl: String? = null): ViewFeed()

}