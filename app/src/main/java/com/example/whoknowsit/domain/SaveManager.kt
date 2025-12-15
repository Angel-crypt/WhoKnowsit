package com.example.whoknowsit.domain

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.whoknowsit.core.GameState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

/**
 * Gestiona el guardado y carga del estado del juego usando DataStore.
 */
class SaveManager(private val context: Context) {

    private val Context.dataStore by preferencesDataStore("SAVED_GAMES")
    private val dataStore = context.dataStore

    companion object {
        private val KEY_GAME_STATE = stringPreferencesKey("game_state_json")

        private val json = Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
        }
    }

    /**
     * Guarda el estado actual del juego en disco.
     * @param state Estado a guardar.
     */
    suspend fun saveGameState(state: GameState) {
        dataStore.edit { prefs ->
            prefs[KEY_GAME_STATE] = json.encodeToString(GameState.serializer(), state)
        }
    }

    /**
     * Flujo que emite el estado guardado, o null si no existe.
     */
    val loadGameState: Flow<GameState?> =
        dataStore.data.map { prefs ->
            prefs[KEY_GAME_STATE]?.let { stored ->
                runCatching { json.decodeFromString(GameState.serializer(), stored) }.getOrNull()
            }
        }

    /**
     * Verifica si existe una partida guardada.
     */
    suspend fun isSavedGameState(): Boolean =
        dataStore.data.first()[KEY_GAME_STATE] != null

    /**
     * Elimina el estado guardado.
     */
    suspend fun clearSavedGame() {
        dataStore.edit { it.remove(KEY_GAME_STATE) }
    }
}
