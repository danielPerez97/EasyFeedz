package com.example.database

import com.squareup.sqldelight.ColumnAdapter

inline class FeedsId(val id: Long)

val feedsIdColumnAdapter = object: ColumnAdapter<FeedsId, Long>
{
    override fun decode(databaseValue: Long): FeedsId
    {
        return FeedsId(databaseValue)
    }

    override fun encode(value: FeedsId): Long
    {
        return value.id
    }

}