package com.mateuszholik.common

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

abstract class PreferenceAssistant(private val context: Context) {

    protected abstract val preferencesName: String

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
    }

    fun <T> write(key: String, value: T) {
        val edit = sharedPreferences.edit()
        when (value) {
            is String -> edit.putString(key, value)
            is Boolean -> edit.putBoolean(key, value)
            is Long -> edit.putLong(key, value)
            is Float -> edit.putFloat(key, value)
            is Int -> edit.putInt(key, value)
        }
        edit.apply()
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
}

inline fun <reified T> PreferenceAssistant.read(key: String, defValue: T): T =
    this.read(key, defValue, T::class.java)

class PermissionsPreferenceAssistant @Inject constructor(
    @ApplicationContext context: Context
) : PreferenceAssistant(context) {

    override val preferencesName: String = "permissions_preferences"
}
