package com.example.easyfeedz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.easyfeedz.databinding.FeedTwitterEntryItemBinding
import com.example.easyfeedz.databinding.FeedUrlEntryItemBinding
import com.example.easyfeedz.model.ViewFeed
import java.util.*

enum class VIEW_TYPE { FEED_SIMPLE_URL, FEED_TWITTER }

class FeedAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var feedList: List<ViewFeed> = Collections.emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType){
            VIEW_TYPE.FEED_SIMPLE_URL.ordinal ->
                SimpleURLViewHolder(FeedUrlEntryItemBinding.inflate(inflater, parent, false))
            VIEW_TYPE.FEED_TWITTER.ordinal ->
                TwitterViewHolder(FeedTwitterEntryItemBinding.inflate(inflater, parent, false))
            else ->
                SimpleURLViewHolder(FeedUrlEntryItemBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val feed = feedList.get(position)
        when (feed) {
            is ViewFeed.SimpleUrlFeed -> ( (holder as SimpleURLViewHolder).bind(feed))
            is ViewFeed.TwitterFeed -> ( (holder as TwitterViewHolder).bind(feed))
        }
    }

    override fun getItemCount(): Int = feedList.size

    override fun getItemViewType(position: Int): Int {
        val feed = feedList.get(position)

        return when(feed) {
            is ViewFeed.SimpleUrlFeed -> VIEW_TYPE.FEED_SIMPLE_URL.ordinal
            is ViewFeed.TwitterFeed -> VIEW_TYPE.FEED_TWITTER.ordinal
        }
    }

    fun setData(newData: List<ViewFeed>)
    {
        feedList = newData
        notifyDataSetChanged()
    }


    class SimpleURLViewHolder(private val binding: FeedUrlEntryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(feed: ViewFeed.SimpleUrlFeed) {
            binding.feedSimpleUrlUrl.text = feed.url
        }
    }

    class TwitterViewHolder(private val binding: FeedTwitterEntryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(feed: ViewFeed.TwitterFeed) {
            binding.feedTwitterName.text = feed.name
            binding.feedTwitterUsername.text = feed.username
            binding.feedTwitterTweetText.text = feed.tweetText
        }
    }



}