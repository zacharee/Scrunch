package com.denytheflowerpot.scrunch.managers

import android.content.Context
import android.content.SharedPreferences

class SettingsManager(private val context: Context) {
    class Keys {
        companion object {
            val UnfoldSoundURL = "unfoldSoundURL"
            val FoldSoundURL = "foldSoundURL"
            val ServiceStarted = "serviceStarted"
            val Volume = "volume"
        }
    }

    val prefs: SharedPreferences by lazy {
        context.getSharedPreferences("${context.packageName}.PREFS", Context.MODE_PRIVATE)
    }

    var unfoldSoundURL: String
        get() = prefs.getString(Keys.UnfoldSoundURL, "") ?: ""
        set(value) {
            prefs.edit().putString(Keys.UnfoldSoundURL, value).apply()
        }

    var foldSoundURL: String
        get() = prefs.getString(Keys.FoldSoundURL, "") ?: ""
        set(value) {
            prefs.edit().putString(Keys.FoldSoundURL, value).apply()
        }

    var serviceStarted: Boolean
        get() = prefs.getBoolean(Keys.ServiceStarted, false)
        set(value) {
            prefs.edit().putBoolean(Keys.ServiceStarted, value).apply()
        }

    var volume: Float
        get() = prefs.getFloat(Keys.Volume, 1F)
        set(value) {
            prefs.edit().putFloat(Keys.Volume, value).apply()
        }
}