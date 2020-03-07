package com.example.database

import com.easyfeedz.database.Database
import com.easyfeedz.database.Feeds
import com.easyfeedz.database.FeedsQueries
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

    @Test
    fun testFeedInsert()
    {
        val feedQueries: FeedsQueries = database.feedsQueries
        Assertions.assertEquals(0, feedQueries.selectAll().executeAsList().size )

        feedQueries.insert("Beer")
        feedQueries.insert("Smash")
        feedQueries.insert("Movies")
        feedQueries.insert("The Division")

        Assertions.assertEquals( 4, feedQueries.selectAll().executeAsList().size )
    }
}