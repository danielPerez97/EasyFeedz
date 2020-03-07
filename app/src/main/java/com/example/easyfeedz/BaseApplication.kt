package com.example.easyfeedz

import android.app.Application
import com.easyfeedz.database.Database
import com.easyfeedz.database.Database.Companion.Schema
import com.easyfeedz.database.Database.Companion.invoke
import com.easyfeedz.database.Feeds
import com.easyfeedz.database.TweetSource
import com.easyfeedz.database.UrlSource
import com.example.database.UrlSourceIdAdapter
import com.example.database.feedsIdColumnAdapter
import com.example.database.tweetSourceIdAdapter
import com.example.easyfeedz.di.AppInjector
import com.example.easyfeedz.di.AppModule
import com.example.easyfeedz.di.DaggerAppInjector
import com.example.easyfeedz.di.DatabaseModule
import com.squareup.sqldelight.android.AndroidSqliteDriver


class BaseApplication: Application()
{

    lateinit var injector: AppInjector
        private set

    override fun onCreate()
    {
        super.onCreate()

        val driver = AndroidSqliteDriver(Schema, applicationContext, "DataFeeds.db")

        injector = DaggerAppInjector.builder()
            .appModule( AppModule(applicationContext) )
            .databaseModule(
                DatabaseModule(invoke(
                    driver,
                    Feeds.Adapter(feedsIdColumnAdapter),
                    TweetSource.Adapter(tweetSourceIdAdapter, feedsIdColumnAdapter),
                    UrlSource.Adapter(UrlSourceIdAdapter, feedsIdColumnAdapter)
                ))
            )
            .build()
    }
}