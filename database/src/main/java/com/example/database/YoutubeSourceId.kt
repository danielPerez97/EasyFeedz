package com.example.database

import com.squareup.sqldelight.ColumnAdapter

class YoutubeSourceId(val id: Long)

val youtubeSourceIdAdapter = object: ColumnAdapter<YoutubeSourceId, Long>
{
    override fun decode(databaseValue: Long): YoutubeSourceId
    {
        return YoutubeSourceId(databaseValue)
    }

    override fun encode(value: YoutubeSourceId): Long
    {
        return value.id
    }

}