package com.example.easyfeedz

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.easyfeedz.databinding.FeedTwitchEntryBinding
import com.example.easyfeedz.databinding.FeedTwitterEntryItemBinding
import com.example.easyfeedz.databinding.FeedUrlEntryItemBinding
import com.example.easyfeedz.databinding.FeedYoutubeEntryItemBinding
import com.example.easyfeedz.model.ViewFeed
import com.squareup.picasso.Picasso
import java.util.*

enum class VIEW_TYPE { FEED_SIMPLE_URL, FEED_TWITTER, FEED_YOUTUBE, FEED_TWITCH }

class FeedAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var feedList: List<ViewFeed> = Collections.emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE.FEED_SIMPLE_URL.ordinal ->
                SimpleURLViewHolder(FeedUrlEntryItemBinding.inflate(inflater, parent, false))
            VIEW_TYPE.FEED_TWITTER.ordinal ->
                TwitterViewHolder(FeedTwitterEntryItemBinding.inflate(inflater, parent, false))

            VIEW_TYPE.FEED_TWITCH.ordinal ->
                TwitchViewHolder(FeedTwitchEntryBinding.inflate(inflater, parent, false))
            VIEW_TYPE.FEED_YOUTUBE.ordinal ->
                YoutubeViewHolder(FeedYoutubeEntryItemBinding.inflate(inflater, parent, false))
            else ->
                SimpleURLViewHolder(FeedUrlEntryItemBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val feed = feedList.get(position)
        when (feed) {
            is ViewFeed.SimpleUrlFeed -> ((holder as SimpleURLViewHolder).bind(feed))
            is ViewFeed.TwitterFeed -> ((holder as TwitterViewHolder).bind(feed))
            is ViewFeed.TwitchFeed -> ((holder as TwitchViewHolder).bind(feed))
            is ViewFeed.YoutubeFeed -> ((holder as YoutubeViewHolder).bind(feed))
        }
    }

    override fun getItemCount(): Int = feedList.size

    override fun getItemViewType(position: Int): Int {
        val feed = feedList.get(position)

        return when (feed) {
            is ViewFeed.SimpleUrlFeed -> VIEW_TYPE.FEED_SIMPLE_URL.ordinal
            is ViewFeed.TwitterFeed -> VIEW_TYPE.FEED_TWITTER.ordinal
            is ViewFeed.YoutubeFeed -> VIEW_TYPE.FEED_YOUTUBE.ordinal
            is ViewFeed.TwitchFeed -> VIEW_TYPE.FEED_TWITCH.ordinal
        }
    }

    fun setData(newData: List<ViewFeed>) {
        feedList = newData
        notifyDataSetChanged()
    }


    class SimpleURLViewHolder(private val binding: FeedUrlEntryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(feed: ViewFeed.SimpleUrlFeed) {
            binding.feedSimpleUrlUrl.text = feed.url

            val webpage: Uri = Uri.parse(feed.url)
            binding.root.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, webpage)
                binding.root.context.startActivity(intent)
            }
        }
    }

    class TwitterViewHolder(private val binding: FeedTwitterEntryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(feed: ViewFeed.TwitterFeed) {
            binding.feedTwitterName.text = feed.name
            binding.feedTwitterUsername.text = feed.username
            binding.feedTwitterTweetText.text = feed.tweetText
        }
    }

    class TwitchViewHolder(private val binding: FeedTwitchEntryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(feed: ViewFeed.TwitchFeed) {
            binding.feedTwitchTitle.text = feed.channel
            binding.feedTwitchDesc.text = feed.description

            val webpage: Uri = Uri.parse(feed.url)
            binding.root.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, webpage)
                binding.root.context.startActivity(intent)
            }
        }
    }

    class YoutubeViewHolder(private val binding: FeedYoutubeEntryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(feed: ViewFeed.YoutubeFeed) {
            binding.feedYoutubeTitle.text = feed.title
            binding.feedYoutubeChannel.text = feed.channel
            binding.feedYoutubeText.text = feed.description

            val webpage: Uri = Uri.parse(feed.url)
            binding.root.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, webpage)
                binding.root.context.startActivity(intent)
            }
            val imageUri = Uri.parse(feed.thumbUrl)
            Picasso.get().load(imageUri).into(binding.feedYoutubeThumbnail)
        }
    }



}