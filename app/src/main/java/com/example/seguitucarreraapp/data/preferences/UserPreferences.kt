package com.example.seguitucarreraapp.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences(
    private val context: Context
) {

    companion object {
        private val SELECTED_CAREER_ID =
            stringPreferencesKey("selected_career_id")
    }

    val selectedCareerId: Flow<String?> =
        context.dataStore.data.map { prefs ->
            prefs[SELECTED_CAREER_ID]
        }

    suspend fun setSelectedCareer(careerId: String) {
        context.dataStore.edit { prefs ->
            prefs[SELECTED_CAREER_ID] = careerId
        }
    }
}
