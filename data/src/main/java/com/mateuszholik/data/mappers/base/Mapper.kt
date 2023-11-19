package com.mateuszholik.data.mappers.base

internal interface Mapper<TInput, TOutput> {

    suspend fun map(param: TInput): TOutput
}
