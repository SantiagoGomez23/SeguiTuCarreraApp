package com.example.seguitucarreraapp.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("onboarding_prefs")

class OnboardingPreferences(private val context: Context) {

    companion object {
        private val ONBOARDING_COMPLETED =
            booleanPreferencesKey("onboarding_completed")
    }

    val onboardingCompleted: Flow<Boolean> =
        context.dataStore.data.map { prefs ->
            prefs[ONBOARDING_COMPLETED] ?: false
        }

    suspend fun setCompleted() {
        context.dataStore.edit { prefs ->
            prefs[ONBOARDING_COMPLETED] = true
        }
    }
}
