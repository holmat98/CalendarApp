package com.mateuszholik.domain.models

sealed class Result<T> {

    data class Success<T>(val data: T) : Result<T>()

    class NoData<T> : Result<T>()
}
