package com.example.easyfeedz.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.easyfeedz.database.Database
import com.example.database.FeedsId
import com.example.easyfeedz.model.ViewFeed
import com.example.easyfeedz.model.ViewHobby
import com.squareup.sqldelight.runtime.rx.asObservable
import com.squareup.sqldelight.runtime.rx.mapToList
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(val database: Database): ViewModel()
{
    fun data(feedId: FeedsId): Observable<List<ViewFeed>>
    {
        populateDummyList(feedId)
        val twitterFeeds: Observable<List<ViewFeed.TwitterFeed>> = database.tweetSourceQueries.selectByFeedId(feedId)
            .asObservable(Schedulers.io())
            .mapToList()
            .map { list ->
                list.map { ViewFeed.TwitterFeed(it.author, it.author, it.tweet, it.url) }
            }

        val simpleUrlFeeds: Observable<List<ViewFeed.SimpleUrlFeed>> = database.urlSourceQueries.selectByFeedId(feedId)
            .asObservable(Schedulers.io())
            .mapToList()
            .map { list ->
                list.map { ViewFeed.SimpleUrlFeed(it.url) }
            }

        val youtubeFeeds: Observable<List<ViewFeed.YoutubeFeed>> = database.youtubeSourceQueries.selectByFeedId(feedId)
            .asObservable(Schedulers.io())
            .mapToList()
            .map { list ->
                list.map { ViewFeed.YoutubeFeed(it.channel, it.channel, it.url, it.pictureurl ?: "", it.description ?: "") }
            }

        val twitchFeeds: Observable<List<ViewFeed.TwitchFeed>> = database.twitchSourceQueries.selectByFeedId(feedId)
            .asObservable(Schedulers.io())
            .mapToList()
            .map { list ->
                list.map { ViewFeed.TwitchFeed(it.channel, it.url, it.description ?: "") }
            }
//            Observable.just(listOf(ViewFeed.TwitchFeed("C9Mang0", "twitch.tv/mang0",
//            "Pro SSBM Player Channel")))

        return Observables.zip(twitterFeeds, simpleUrlFeeds, youtubeFeeds, twitchFeeds) { first: List<ViewFeed>,
                                                                                          second: List<ViewFeed>,
                                                                                          third: List<ViewFeed>,
                                                                                            fourth: List<ViewFeed> ->
            val mergedList = mutableListOf<ViewFeed>()
            mergedList.addAll(first)
            mergedList.addAll(second)
            mergedList.addAll(third)
            mergedList.addAll(fourth)
            return@zip mergedList.toList()
        }
    }

    fun sideBarList(): Observable<List<ViewHobby>>
    {
        return database.feedsQueries.selectAll().asObservable(Schedulers.io())
            .mapToList()
            .map { list -> list.map { ViewHobby(it.name) } }
    }

    private fun populateDummyList(feedId: FeedsId)
    {
        // Populate twitter data
        for (x in 0 until 1) {
            database.tweetSourceQueries.insert(feedId, "Zain", "I AM STUPID $x", "www.twitter.com")
        }

        // Populate simple url data
        for (x in 0 until 1 ) {
            database.urlSourceQueries.insert(feedId, "Joogle $x", "www.google.com")
        }

        // Populate Youtube Feeds
        for(x in 0 until 3) {
            database.youtubeSourceQueries.insert(feedId, "Scattervolt", "https://www.youtube.com/channel/UCl6ulw6cn3npwxkg_56Ee_w", "Sick channel about PC builds",
                "Sick channel about PC buildss")
        }
    }
}