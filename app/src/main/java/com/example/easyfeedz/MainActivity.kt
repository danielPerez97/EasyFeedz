package com.example.easyfeedz

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.easyfeedz.databinding.ActivityMainBinding
import com.example.easyfeedz.model.ViewFeed
import javax.inject.Inject

class MainActivity : AppCompatActivity()
{

    lateinit var binding: ActivityMainBinding
    @Inject lateinit var appContext: Context

    lateinit var feedAdapter : FeedAdapter
    lateinit var feedList : MutableList<ViewFeed>

    override fun onCreate(savedInstanceState: Bundle?)
    {
        injector().inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleRecyclerView()
    }

    private fun handleRecyclerView(){
        populateDummyList()
        feedAdapter = FeedAdapter(feedList)
        binding.recyclerviewFeed.adapter = feedAdapter
        binding.recyclerviewFeed.layoutManager = LinearLayoutManager(baseContext)
    }

    private fun populateDummyList(){
        feedList = mutableListOf()
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
    }
}
