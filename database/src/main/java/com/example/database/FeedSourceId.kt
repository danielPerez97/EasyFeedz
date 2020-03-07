package com.example.database

import com.squareup.sqldelight.ColumnAdapter

inline class FeedSourceId(val id: Long)

val feedSourceIdAdapter = object: ColumnAdapter<FeedSourceId, Long>
{
    override fun decode(databaseValue: Long): FeedSourceId
    {
        return FeedSourceId(databaseValue)
    }

    override fun encode(value: FeedSourceId): Long
    {
        return value.id
    }

}