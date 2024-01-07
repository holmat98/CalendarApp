package com.mateuszholik.data.mappers.base

import android.database.Cursor

internal interface Mapper<TOutput> {

    suspend fun map(cursor: Cursor): TOutput
}
