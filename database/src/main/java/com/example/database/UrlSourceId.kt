package com.example.database

import com.squareup.sqldelight.ColumnAdapter

class UrlSourceId(val id: Long)

val UrlSourceIdAdapter = object: ColumnAdapter<UrlSourceId, Long>
{
    override fun decode(databaseValue: Long): UrlSourceId
    {
        return UrlSourceId(databaseValue)
    }

    override fun encode(value: UrlSourceId): Long
    {
        return value.id
    }

}