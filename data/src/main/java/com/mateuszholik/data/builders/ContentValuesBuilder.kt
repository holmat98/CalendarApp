package com.mateuszholik.data.builders

import android.content.ContentValues

internal object ContentValuesBuilder {

    fun contentValuesBuilder(config: ContentValues.() -> Unit): ContentValues =
        ContentValues().apply(config)
}
