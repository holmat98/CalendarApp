package com.mateuszholik.data.factories.models

import android.content.ContentValues
import android.net.Uri

internal data class UpdateData(
    val uri: Uri,
    val values: ContentValues,
)
