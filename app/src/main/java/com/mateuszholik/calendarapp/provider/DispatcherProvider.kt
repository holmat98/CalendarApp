package com.mateuszholik.calendarapp.provider

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

interface DispatcherProvider {

    fun io(): CoroutineDispatcher
}

class DispatcherProviderImpl @Inject constructor() : DispatcherProvider {

    override fun io(): CoroutineDispatcher = Dispatchers.IO
}
