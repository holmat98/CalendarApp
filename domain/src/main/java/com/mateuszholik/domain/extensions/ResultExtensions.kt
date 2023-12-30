package com.mateuszholik.domain.extensions

import com.mateuszholik.domain.models.Result

internal fun <T, R> T?.asResult(mapper: T.() -> R): Result<R> =
    this?.let { Result.Success(it.mapper()) } ?: Result.NoData()
