package com.chumachenko.core.data.storage

import android.content.Context

class DrinksPreferencesManager(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(
        "com.chumachenko.core.preferences",
        Context.MODE_PRIVATE
    )

    var lastQuery: String
        set(value) {
            sharedPreferences.edit().apply {
                putString(KEY_LAST_QUERY, value)
            }.apply()
        }
        get() {
            return sharedPreferences.getString(KEY_LAST_QUERY, "") ?: ""
        }

    companion object {
        private const val KEY_LAST_QUERY = "KEY_LAST_QUERY"
    }
}