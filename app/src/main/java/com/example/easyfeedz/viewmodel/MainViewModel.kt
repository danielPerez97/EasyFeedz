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
            .doOnNext { Log.i("MainViewModel", "SIZE TWEETS: ${it.size}") }

        val simpleUrlFeeds: Observable<List<ViewFeed.SimpleUrlFeed>> = database.urlSourceQueries.selectByFeedId(feedId)
            .asObservable(Schedulers.io())
            .mapToList()
            .map { list ->
                list.map { ViewFeed.SimpleUrlFeed(it.url) }
            }
            .doOnNext { Log.i("MainViewModel", "SIZE URL: ${it.size}") }

        val youtubeFeeds: Observable<List<ViewFeed.YoutubeFeed>> = Observable.just(listOf(ViewFeed.YoutubeFeed("ScatterVolt", "Prank gone wrong",
            "https://www.youtube.com/channel/UCl6ulw6cn3npwxkg_56Ee_w", "https://yt3.ggpht.com/a/AATXAJy9W2iA_2fLGZ9bgPcFAKf3GKfspq8bhEkP=s288-c-k-c0xffffffff-no-rj-mo",
            "High-quality youtube channel about putting PC builds together.")))

        val twitchFeeds: Observable<List<ViewFeed.TwitchFeed>> = Observable.just(listOf(ViewFeed.TwitchFeed("C9Mang0", "twitch.tv/mang0",
            "Pro SSBM Player Channel")))

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

        for (x in 0 until 1 ) {
            database.urlSourceQueries.insert(feedId, "Joogle $x", "www.google.com")
        }
        for (x in 0 until 1) {
            database.tweetSourceQueries.insert(feedId, "Zain", "I AM STUPID $x", "www.twitter.com")
            Log.i("MainViewModel","INSERTING STUPID")
        }
    }
}