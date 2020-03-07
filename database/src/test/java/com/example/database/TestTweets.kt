package com.example.database

import com.easyfeedz.database.*
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class TestTweets
{
    private val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    init
    {
        Database.Schema.create(driver)
    }
    private val database = Database.invoke(
        driver,
        Feeds.Adapter(feedsIdColumnAdapter),
        TweetSource.Adapter(tweetSourceIdAdapter, feedsIdColumnAdapter),
        TwitchSource.Adapter(twitchSourceIdAdapter, feedsIdColumnAdapter),
        UrlSource.Adapter(UrlSourceIdAdapter, feedsIdColumnAdapter),
        YoutubeSource.Adapter(youtubeSourceIdAdapter, feedsIdColumnAdapter)
    )
    private val feedQueries: FeedsQueries = database.feedsQueries
    private val tweetsQueries: TweetSourceQueries = database.tweetSourceQueries


    //----------------------------------------------------------------------------------------------
    //Feeds Code Tests
    //----------------------------------------------------------------------------------------------
    @Test
    fun testTweetsInsert()
    {
        assertEquals(0, tweetsQueries.selectAll().executeAsList().size )
        insertDummyTweetsData()
        assertEquals(1, tweetsQueries.selectAll().executeAsList().size )
    }

    @Test
    fun testTweetsRemove()
    {
        assertEquals(0, tweetsQueries.selectAll().executeAsList().size )
        insertDummyTweetsData()
        assertEquals(1, tweetsQueries.selectAll().executeAsList().size )

        val data: List<TweetSource> = tweetsQueries.selectAll().executeAsList()
        tweetsQueries.remove(data[0]._id)

        assertEquals(0, tweetsQueries.selectAll().executeAsList().size )
    }

    //----------------------------------------------------------------------------------------------
    //Insertion Methods
    //----------------------------------------------------------------------------------------------
    private fun insertDummyFeedsData()
    {
        feedQueries.insert("Beer")
        feedQueries.insert("Smash")
        feedQueries.insert("Movies")
        feedQueries.insert("The Division")
    }


    private fun insertDummyTweetsData()
    {
        insertDummyFeedsData()
        val data: List<Feeds> = feedQueries.selectAll().executeAsList()
        tweetsQueries.insert(data[0]._id,"Person","Tweet", "twitter.com")
    }
}