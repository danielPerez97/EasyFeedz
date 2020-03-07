package com.example.database

import com.easyfeedz.database.*
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class TestYoutube
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
    private val youtubeQueries: YoutubeSourceQueries = database.youtubeSourceQueries

    //----------------------------------------------------------------------------------------------
    //Feeds Code Tests
    //----------------------------------------------------------------------------------------------

    @Test
    fun testYoutubeInsert()
    {
        assertEquals(0, youtubeQueries.selectAll().executeAsList().size )
        insertDummyYoutubeData()
        assertEquals(1, youtubeQueries.selectAll().executeAsList().size )
    }

    @Test
    fun testYoutubeRemove()
    {
        assertEquals(0, youtubeQueries.selectAll().executeAsList().size )
        insertDummyYoutubeData()
        assertEquals(1, youtubeQueries.selectAll().executeAsList().size )

        val data: List<YoutubeSource> = youtubeQueries.selectAll().executeAsList()
        youtubeQueries.remove(data[0]._id)

        assertEquals(0, youtubeQueries.selectAll().executeAsList().size )
    }





    //----------------------------------------------------------------------------------------------
    //Insertion Methods
    //------------------------------------------------------------------

    private fun insertDummyFeedsData()
    {
        feedQueries.insert("Beer")
        feedQueries.insert("Smash")
        feedQueries.insert("Movies")
        feedQueries.insert("The Division")
    }

    private fun insertDummyYoutubeData()
    {
        insertDummyFeedsData()
        val data: List<Feeds> = feedQueries.selectAll().executeAsList()
        youtubeQueries.insert(data[0]._id,"Channel","youtube.com", "picture.com", "Description")
    }
}




