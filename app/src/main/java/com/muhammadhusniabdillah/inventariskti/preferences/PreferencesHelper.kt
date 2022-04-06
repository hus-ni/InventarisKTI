package com.muhammadhusniabdillah.inventariskti.preferences

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper(context: Context) {

    private val SHARED_PREFS_FILE = "loginPreference"
    private var sharedPref: SharedPreferences
    val editor: SharedPreferences.Editor

    init {
        sharedPref = context.getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE)
        editor = sharedPref.edit()
    }

    fun putString(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    fun getString(key: String): String? {
        return sharedPref.getString(key, null)
    }

    fun putBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value).apply()
    }

    fun getBoolean(key: String): Boolean {
        return sharedPref.getBoolean(key, false)
    }

    fun clear() {
        editor.clear().apply()
    }

}