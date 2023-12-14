package com.mateuszholik.data.factories.models

import android.content.ContentValues
import android.net.Uri

data class UpdateData(
    val uri: Uri,
    val values: ContentValues,
)
