package com.example.database

import com.squareup.sqldelight.ColumnAdapter

class RedditSourceId(val id: Long)

val redditSourceIdAdapter = object: ColumnAdapter<RedditSourceId, Long>
{
    override fun decode (databaseValue: Long): RedditSourceId
    {
        return RedditSourceId(databaseValue)
    }

    override fun encode(value: RedditSourceId): Long
    {
        return value.id
    }
}