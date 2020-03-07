package com.example.database

import com.easyfeedz.database.*
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class TestFeeds
{
    private val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    init
    {
        Database.Schema.create(driver)
    }
    private val database = Database.invoke(driver, FeedSource.Adapter(feedSourceIdAdapter, feedsIdColumnAdapter), Feeds.Adapter(feedsIdColumnAdapter))
    private val feedQueries: FeedsQueries = database.feedsQueries
    private val sourceQueries: FeedSourceQueries = database.feedSourceQueries


    //----------------------------------------------------------------------------------------------
    //Feeds Code Tests
    //----------------------------------------------------------------------------------------------
    @Test
    fun testFeedInsert()
    {
        Assertions.assertEquals(0, feedQueries.selectAll().executeAsList().size )
        insertDummyFeedsData()
        Assertions.assertEquals( 4, feedQueries.selectAll().executeAsList().size )
    }


    @Test
    fun testFeedRemove()
    {
        insertDummyFeedsData()
        Assertions.assertEquals( 4, feedQueries.selectAll().executeAsList().size )

        val data: List<Feeds> = feedQueries.selectAll().executeAsList()
        feedQueries.remove(data[0]._id)

        Assertions.assertEquals( 3, feedQueries.selectAll().executeAsList().size )
    }

    @Test
    fun testFeedUpdate()
    {
        insertDummyFeedsData()
        Assertions.assertEquals(4,feedQueries.selectAll().executeAsList().size)

        val data: List<Feeds> = feedQueries.selectAll().executeAsList()
        val id: FeedsId = data[0]._id

        // Update
        feedQueries.update(id, "Woodcutting")
        Assertions.assertEquals(4,feedQueries.selectAll().executeAsList().size)

        // Test the update
        val feed = feedQueries.selectById(id).executeAsOne()
        Assertions.assertEquals("Woodcutting", feed.name)

    }

    //----------------------------------------------------------------------------------------------
    //FeedSource Code Tests
    //----------------------------------------------------------------------------------------------
    @Test
    fun testFeedSourceRemove()
    {
        Assertions.assertEquals( 0, sourceQueries.selectAll().executeAsList().size )
        insertDummyFeedsData()
        insertDummySourceData()
        Assertions.assertEquals( 1, sourceQueries.selectAll().executeAsList().size )
        val data: List<FeedSource> = sourceQueries.selectAll().executeAsList()
        sourceQueries.remove(data[0]._id)
        Assertions.assertEquals( 0, sourceQueries.selectAll().executeAsList().size )
    }

    @Test
    fun testFeedSourceInsert()
    {
        insertDummyFeedsData()
        insertDummySourceData()
        Assertions.assertEquals( 1, sourceQueries.selectAll().executeAsList().size )
    }

    @Test
    fun testFeedSourceInsertDesc()
    {
        insertDummyFeedsData()
        insertDummySourceDataDesc()
        Assertions.assertEquals( 1, sourceQueries.selectAll().executeAsList().size )

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
        sourceQueries.insert(data[0]._id, "youtube.com", "John Smith")
    }

    private fun insertDummySourceDataDesc()
    {
        insertDummyFeedsData()
        val data: List<Feeds> = feedQueries.selectAll().executeAsList()
        sourceQueries.insertDesc(data[0]._id, "youtube.com", "John Smith","Blah Blah")
    }






}

