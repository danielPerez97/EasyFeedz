package com.example.easyfeedz

sealed class UrlType
{
    class Unknown(val text: String): UrlType()
    class SimpleUrl(val text: String): UrlType()
    class TwitchUrl(val text: String): UrlType()
    class TweetUrl(val text: String): UrlType()
    class YoutubeUrl(val text: String): UrlType()
}