package com.mateuszholik.domain.usecases.base

interface UseCase<TOutput> {

    suspend operator fun invoke(): TOutput
}
