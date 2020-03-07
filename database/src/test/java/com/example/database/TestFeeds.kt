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
    private val database = Database.invoke(driver)
    val feedQueries: FeedsQueries = database.feedsQueries
    val sourceQueries: FeedSourceQueries = database.feedSourceQueries

    @Test
    fun testFeedInsert()
    {
        Assertions.assertEquals(0, feedQueries.selectAll().executeAsList().size )
        insertDummyData()
        Assertions.assertEquals( 4, feedQueries.selectAll().executeAsList().size )
    }



    @Test
    fun testFeedRemove()
    {
        insertDummyData()
        Assertions.assertEquals( 4, feedQueries.selectAll().executeAsList().size )

        val data: List<Feeds> = feedQueries.selectAll().executeAsList()
        feedQueries.remove(data[0]._id)

        Assertions.assertEquals( 3, feedQueries.selectAll().executeAsList().size )

    }

    @Test
    fun testFeedSourceInsert()
    {
        insertDummyData()
        insertDummySourceData()
        val data: List<FeedSource> = sourceQueries.selectAll().executeAsList()
//        sourceQueries.insert(data[0]._id)
        Assertions.assertEquals( 1, sourceQueries.selectAll().executeAsList().size )

    }

    fun insertDummyData()
    {
        feedQueries.insert("Beer")
        feedQueries.insert("Smash")
        feedQueries.insert("Movies")
        feedQueries.insert("The Division")
    }

    fun insertDummySourceData()
    {
        sourceQueries.insert(1L, "youtube.com", "John Smith")
    }


    fun insertDummySourceDataDesc()
    {
        sourceQueries.insertDesc(7L, "youtube.com", "John Smith","Blah Blah")
    }

}