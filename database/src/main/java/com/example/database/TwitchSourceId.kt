package com.example.database

import com.squareup.sqldelight.ColumnAdapter

class TwitchSourceId(val id: Long)

val twitchSourceIdAdapter = object: ColumnAdapter<TwitchSourceId, Long>
{
    override fun decode (databaseValue: Long): TwitchSourceId
    {
        return TwitchSourceId(databaseValue)
    }

    override fun encode(value: TwitchSourceId): Long
    {
        return value.id
    }
}