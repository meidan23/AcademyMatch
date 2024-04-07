package com.cropx.academymatch

import android.content.Context
import android.content.SharedPreferences

object PreferencesHelper {

    private val USER_DATA = Pair("USER_DATA", null)

    private lateinit var preferences: SharedPreferences
    private var editor: SharedPreferences.Editor? = null

    fun init(context: Context) {
        preferences = context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        editor = preferences.edit()
    }

    private inline fun SharedPreferences.edit(
        operation: (SharedPreferences.Editor) -> Unit
    ) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var userData: String?
        get() = preferences.getString(USER_DATA.first, USER_DATA.second)
        set(value) = preferences.edit {
            it.putString(USER_DATA.first, value)
        }
}