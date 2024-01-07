package com.mateuszholik.domain.usecases.base

import com.mateuszholik.domain.models.Result

interface ParameterizedUseCase<TInput, TOutput> {

    suspend operator fun invoke(param: TInput): TOutput
}

interface ParameterizedResultUseCase<TInput, TOutput> : ParameterizedUseCase<TInput, Result<TOutput>>
