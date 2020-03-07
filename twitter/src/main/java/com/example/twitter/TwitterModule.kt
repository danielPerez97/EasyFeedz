package com.example.twitter

import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class TwitterModule
{
    @Provides
    @Named("Mango")
    fun provideMangoTweets(): List<Tweet>
    {
        return arrayListOf<Tweet>().apply {
            addAll(mangoTweets())
            addAll(zainTweets())
        }
    }

    @Provides
    @Named("CSGO")
    fun provideCSGOTweets(): List<Tweet>
    {
        return arrayListOf<Tweet>().apply {
            csgoTweets()
        }
    }
}