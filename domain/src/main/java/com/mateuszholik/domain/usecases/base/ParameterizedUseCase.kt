package com.mateuszholik.domain.usecases.base

interface ParameterizedUseCase<TInput, TOutput> {

    suspend operator fun invoke(param: TInput): TOutput
}
