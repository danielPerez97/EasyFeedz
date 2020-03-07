package com.example.database

import com.squareup.sqldelight.ColumnAdapter

class TweetSourceId(val id: Long)

val tweetSourceIdAdapter = object: ColumnAdapter<TweetSourceId, Long>
{
    override fun decode(databaseValue: Long): TweetSourceId
    {
        return TweetSourceId(databaseValue)
    }

    override fun encode(value: TweetSourceId): Long
    {
        return value.id
    }

}