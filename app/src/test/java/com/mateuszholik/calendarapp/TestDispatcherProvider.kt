package com.mateuszholik.calendarapp

import com.mateuszholik.calendarapp.provider.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal class TestDispatcherProvider : DispatcherProvider {

    override fun io(): CoroutineDispatcher = Dispatchers.Unconfined

    override fun main(): CoroutineDispatcher = Dispatchers.Unconfined
}
