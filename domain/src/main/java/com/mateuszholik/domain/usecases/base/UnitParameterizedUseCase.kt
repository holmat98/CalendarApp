package com.mateuszholik.domain.usecases.base

interface UnitParameterizedUseCase<TInput> {

    suspend operator fun invoke(param: TInput)
}
