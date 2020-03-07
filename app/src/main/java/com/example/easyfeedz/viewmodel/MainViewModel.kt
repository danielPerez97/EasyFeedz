package com.example.easyfeedz.viewmodel

import androidx.lifecycle.ViewModel
import com.easyfeedz.database.Database
import com.example.easyfeedz.model.ViewFeed
import io.reactivex.Observable
import javax.inject.Inject

class MainViewModel @Inject constructor(val database: Database): ViewModel()
{
    fun data(): Observable<List<ViewFeed>>
    {
        return Observable.just(populateDummyList())
    }

    private fun populateDummyList(): List<ViewFeed>
    {
        val feedList = mutableListOf<ViewFeed>()
        for (x in 0 until 3 ) {
            val feed = ViewFeed.SimpleUrlFeed("www.google.com")
            feedList.add(feed)
        }
        for (x in 0 until 2) {
            val feed = ViewFeed.TwitterFeed("John Doe", "@johndoe", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")
            feedList.add(feed)
        }
        val feed = ViewFeed.TwitterFeed("Jane Arse", "@jarse", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")
        feedList.add(feed)
        return feedList
    }
}