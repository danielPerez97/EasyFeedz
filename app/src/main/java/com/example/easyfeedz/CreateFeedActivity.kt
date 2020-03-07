package com.example.easyfeedz

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.easyfeedz.databinding.ActivityCreateFeedBinding
import com.example.easyfeedz.di.viewmodel.ViewModelFactory
import com.example.easyfeedz.viewmodel.CreateFeedViewModel
import com.jakewharton.rxbinding3.widget.textChangeEvents
import javax.inject.Inject

class CreateFeedActivity : AppCompatActivity()
{
    @Inject lateinit var factory: ViewModelFactory
    lateinit var binding: ActivityCreateFeedBinding
    lateinit var createFeedViewModel: CreateFeedViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        injector().inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityCreateFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createFeedViewModel = ViewModelProvider(this, factory).get(CreateFeedViewModel::class.java)

        val disposable = binding.urlEdit.textChangeEvents()
            .filter { it.text.isNotBlank() && it.text.isNotEmpty() }
            .map { it.text.toString() }
            .map {
                return@map when {
                    it.contains("twitter") -> {
                        UrlType.TweetUrl(it)
                    }
                    it.contains("youtube") || it.contains("youtu.be") -> {
                        UrlType.YoutubeUrl(it)
                    }
                    it.contains("twitch") -> {
                        UrlType.TwitchUrl(it)
                    }
                    it.startsWith("http://") || it.startsWith("https://") -> {
                        UrlType.SimpleUrl(it)
                    }
                    else -> UrlType.Unknown(it)
                }
            }
            .subscribe {
                when(it)
                {
                    is UrlType.Unknown ->
                    {
                        Log.i("CREATEFEED", "Unknown ${it.text}")
                        binding.imageView.load(R.drawable.ic_help_black_24dp)
                    }
                    is UrlType.SimpleUrl ->
                    {
                        Log.i("CREATEFEED", "SimpleUrl ${it.text}")
                        binding.imageView.load(R.drawable.ic_public_black_24dp)
                    }
                    is UrlType.TwitchUrl ->
                    {
                        Log.i("CREATEFEED", "TwitchUrl ${it.text}")
                        binding.imageView.load(R.drawable.twitch_purple)
                    }
                    is UrlType.TweetUrl ->
                    {
                        Log.i("CREATEFEED", "TweetUrl ${it.text}")
                        binding.imageView.load(R.drawable.twitter_logo)
                    }
                    is UrlType.YoutubeUrl ->
                    {
                        Log.i("CREATEFEED", "YoutubeUrl ${it.text}")
                        binding.imageView.load(R.drawable.youtube_logo)
                    }
                }
            }
    }
}
