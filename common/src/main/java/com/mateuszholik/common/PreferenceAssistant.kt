package com.mateuszholik.common

import android.content.Context
import android.content.SharedPreferences

class PreferenceAssistant(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun <T> write(key: String, value: T) {
        val sharedPrefEditor = sharedPreferences.edit()

        when (value) {
            is String -> sharedPrefEditor.putString(key, value)
            is Boolean -> sharedPrefEditor.putBoolean(key, value)
            is Long -> sharedPrefEditor.putLong(key, value)
            is Float -> sharedPrefEditor.putFloat(key, value)
            is Int -> sharedPrefEditor.putInt(key, value)
            else -> error("Wrong type")
        }

        sharedPrefEditor.apply()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> read(key: String, defValue: T, kClass: Class<T>): T =
        when {
            kClass.isAssignableFrom(Boolean::class.java) ->
                sharedPreferences.getBoolean(key, defValue as Boolean) as T
            kClass.isAssignableFrom(Long::class.java) ->
                sharedPreferences.getLong(key, defValue as Long) as T
            kClass.isAssignableFrom(Float::class.java) ->
                sharedPreferences.getFloat(key, defValue as Float) as T
            kClass.isAssignableFrom(Int::class.java) ->
                sharedPreferences.getInt(key, defValue as Int) as T
            else -> (sharedPreferences.getString(key, null) ?: defValue) as T
        }

    private companion object {
        const val PREFERENCES_NAME = "calendar_app_shared_preferences"
    }
}

inline fun <reified T> PreferenceAssistant.read(key: String, defValue: T): T =
    this.read(key, defValue, T::class.java)
