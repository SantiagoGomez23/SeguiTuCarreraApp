package com.example.seguitucarreraapp.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

private val Context.dataStore by preferencesDataStore(name = "career_prefs")

class CareerPreferences(
    private val context: Context
) {

    companion object {
        private val CAREER_ID = stringPreferencesKey("career_id")
    }

    /**
     * Flow reactivo (opcional, para el futuro)
     */
    fun selectedCareerId(): Flow<String?> =
        context.dataStore.data.map { prefs ->
            prefs[CAREER_ID]
        }

    /**
     * Guardar carrera seleccionada
     */
    suspend fun saveCareerId(careerId: String) {
        context.dataStore.edit { prefs ->
            prefs[CAREER_ID] = careerId
        }
    }

    /**
     * Obtener carrera guardada (sincrónico, para ViewModel init)
     */
    fun getCareerId(): String? =
        runBlocking {
            context.dataStore.data.first()[CAREER_ID]
        }
}
