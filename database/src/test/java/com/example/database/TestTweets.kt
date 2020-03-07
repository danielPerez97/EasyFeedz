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
        UrlSource.Adapter(UrlSourceIdAdapter, feedsIdColumnAdapter)
    )
    private val tweetsQueries: TweetSourceQueries = database.tweetSourceQueries


    //----------------------------------------------------------------------------------------------
    //Feeds Code Tests
    //----------------------------------------------------------------------------------------------
    @Test
    fun testTweetsInsert()
    {
        assertEquals(0, tweetsQueries.selectAll().executeAsList().size )

    }

    @Test
    fun testTweetsRemove()
    {

    }

    private fun insertDummySourceTweet()
    {
//        val data: List<TweetsSource>
    }
}