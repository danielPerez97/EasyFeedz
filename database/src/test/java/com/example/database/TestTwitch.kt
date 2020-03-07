package com.example.database

import com.easyfeedz.database.*
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class TestTwitch
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
        UrlSource.Adapter(UrlSourceIdAdapter, feedsIdColumnAdapter)
    )
    private val feedQueries: FeedsQueries = database.feedsQueries
    private val twitchQueries: TwitchSourceQueries = database.twitchSourceQueries


    //----------------------------------------------------------------------------------------------
    //Feeds Code Tests
    //----------------------------------------------------------------------------------------------
    @Test
    fun testTwitchInsert()
    {
        assertEquals(0, twitchQueries.selectAll().executeAsList().size )
        insertDummyTwitchData()
        assertEquals(1, twitchQueries.selectAll().executeAsList().size)
    }

    @Test
    fun testTwitchRemove()
    {
        assertEquals(0, twitchQueries.selectAll().executeAsList().size )
        insertDummyTwitchData()
        assertEquals(1, twitchQueries.selectAll().executeAsList().size)

        val data: List<TwitchSource> = twitchQueries.selectAll().executeAsList()
        twitchQueries.remove(data[0]._id)

        assertEquals(0, twitchQueries.selectAll().executeAsList().size )


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

    private fun insertDummyTwitchData()
    {
        insertDummyFeedsData()
        val data: List<Feeds> = feedQueries.selectAll().executeAsList()
        twitchQueries.insert(data[0]._id, "TwitchUser", "twitch.tv")
    }

}