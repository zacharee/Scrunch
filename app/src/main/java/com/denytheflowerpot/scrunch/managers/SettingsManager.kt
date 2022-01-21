package com.denytheflowerpot.scrunch.managers

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.denytheflowerpot.scrunch.ScrunchApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

class SettingsManager(private val context: Context) {
    private class Keys {
        companion object {
            val UnfoldSoundURL = "unfoldSoundURL"
            val FoldSoundURL = "foldSoundURL"
            val ServiceStarted = "serviceStarted"
            val Volume = "volume"
        }
    }
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = "settings",
        produceMigrations = {
            listOf(
                SharedPreferencesMigration(it, "${it.packageName}.PREFS")
            )
        }
    )

    val unfoldSoundURL: Flow<String>
        get() =

    var unfoldSoundURL: String
        get() = context.dataStore.data.collect {  }
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