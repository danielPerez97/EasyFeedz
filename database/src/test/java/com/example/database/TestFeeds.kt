package com.example.database

import com.easyfeedz.database.*
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class TestFeeds
{
    private val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    init
    {
        Database.Schema.create(driver)
    }
    private val database: Database = Database.invoke(
        driver,
        Feeds.Adapter(feedsIdColumnAdapter),
        TweetSource.Adapter(tweetSourceIdAdapter, feedsIdColumnAdapter),
        TwitchSource.Adapter(twitchSourceIdAdapter, feedsIdColumnAdapter),
        UrlSource.Adapter(UrlSourceIdAdapter, feedsIdColumnAdapter),
        YoutubeSource.Adapter(youtubeSourceIdAdapter, feedsIdColumnAdapter)
    )
    private val feedQueries: FeedsQueries = database.feedsQueries
    private val urlSourceQueries: UrlSourceQueries = database.urlSourceQueries


    //----------------------------------------------------------------------------------------------
    //Feeds Code Tests
    //----------------------------------------------------------------------------------------------
    @Test
    fun testFeedInsert()
    {
        assertEquals(0, feedQueries.selectAll().executeAsList().size )
        insertDummyFeedsData()
        assertEquals( 4, feedQueries.selectAll().executeAsList().size )
    }


    @Test
    fun testFeedRemove()
    {
        insertDummyFeedsData()
        assertEquals( 4, feedQueries.selectAll().executeAsList().size )

        val data: List<Feeds> = feedQueries.selectAll().executeAsList()
        feedQueries.remove(data[0]._id)

        assertEquals( 3, feedQueries.selectAll().executeAsList().size )
    }

    @Test
    fun testFeedUpdate()
    {
        insertDummyFeedsData()
        assertEquals(4,feedQueries.selectAll().executeAsList().size)

        val data: List<Feeds> = feedQueries.selectAll().executeAsList()
        val id: FeedsId = data[0]._id

        // Update
        feedQueries.update(id, "Woodcutting")
        assertEquals(4,feedQueries.selectAll().executeAsList().size)

        // Test the update
        val feed = feedQueries.selectById(id).executeAsOne()
        assertEquals("Woodcutting", feed.name)

    }

    //----------------------------------------------------------------------------------------------
    //FeedSource Code Tests
    //----------------------------------------------------------------------------------------------
    @Test
    fun testFeedSourceRemove()
    {
        assertEquals( 0, urlSourceQueries.selectAll().executeAsList().size )
        insertDummyFeedsData()
        insertDummySourceData()
        assertEquals( 1, urlSourceQueries.selectAll().executeAsList().size )
        val data: List<UrlSource> = urlSourceQueries.selectAll().executeAsList()
        urlSourceQueries.remove(data[0]._id)
        assertEquals( 0, urlSourceQueries.selectAll().executeAsList().size )
    }

    @Test
    fun testFeedSourceInsert()
    {
        insertDummyFeedsData()
        insertDummySourceData()
        assertEquals( 1, urlSourceQueries.selectAll().executeAsList().size )
    }

    @Test
    fun testFeedSourceInsertDesc()
    {
        insertDummyFeedsData()
        insertDummySourceDataDesc()
        assertEquals( 1, urlSourceQueries.selectAll().executeAsList().size )

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

    private fun insertDummySourceData()
    {
        val data: List<Feeds> = feedQueries.selectAll().executeAsList()
        urlSourceQueries.insert(data[0]._id, "youtube.com", "John Smith")
    }

    private fun insertDummySourceDataDesc()
    {
        insertDummyFeedsData()
        val data: List<Feeds> = feedQueries.selectAll().executeAsList()
        urlSourceQueries.insertDesc(data[0]._id, "youtube.com", "John Smith","Blah Blah")
    }
}

