package com.mateuszholik.uicomponents.extensions

import java.util.Locale

internal fun String.capitalized(): String =
    replaceFirstChar {
        if (it.isLowerCase()) {
            it.titlecase(
                Locale.getDefault()
            )
        } else {
            it.toString()
        }
    }
