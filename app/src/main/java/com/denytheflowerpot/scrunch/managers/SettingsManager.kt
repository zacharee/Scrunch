package com.denytheflowerpot.scrunch.managers

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.denytheflowerpot.scrunch.ScrunchApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsManager(private val context: Context) {
    private class Keys {
        companion object {
            public val UnfoldSoundURL = stringPreferencesKey("unfoldSoundURL")
            public val FoldSoundURL = "foldSoundURL"
            public val ServiceStarted = "serviceStarted"
            public val Volume = "volume"
        }
    }
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = "settings",
        produceMigrations = {
            listOf(
                SharedPreferencesMigration(it, "${context.packageName}.PREFS")
            )
        }
    )

    val unfoldSoundURL: Flow<String>
        get() = context.dataStore.data
            .map { prefs ->
                prefs[Keys.UnfoldSoundURL] ?: ""
            }

    suspend fun updateUnfoldSoundURL(url: String) {
        context.dataStore.edit { prefs ->
            prefs[Keys.UnfoldSoundURL] = url
        }
    }

    val foldSoundURL: Flow<String>


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