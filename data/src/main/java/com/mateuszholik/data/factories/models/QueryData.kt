package com.mateuszholik.data.factories.models

import android.net.Uri

internal data class QueryData(
    val uri: Uri,
    val projection: Array<String>,
    val selection: String?,
    val selectionArgs: Array<String>?
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as QueryData

        if (uri != other.uri) return false
        if (!projection.contentEquals(other.projection)) return false
        if (selection != other.selection) return false
        if (!selectionArgs.contentEquals(other.selectionArgs)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uri.hashCode()
        result = 31 * result + projection.contentHashCode()
        result = 31 * result + selection.hashCode()
        result = 31 * result + selectionArgs.contentHashCode()
        return result
    }
}
