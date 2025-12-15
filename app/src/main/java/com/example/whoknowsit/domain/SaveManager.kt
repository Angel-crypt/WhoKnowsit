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

    suspend fun saveGameState(state: GameState) {
        dataStore.edit { prefs ->
            prefs[KEY_GAME_STATE] = json.encodeToString(GameState.serializer(), state)
        }
    }

    val loadGameState: Flow<GameState?> =
        dataStore.data.map { prefs ->
            prefs[KEY_GAME_STATE]?.let { stored ->
                runCatching { json.decodeFromString(GameState.serializer(), stored) }.getOrNull()
            }
        }

    suspend fun isSavedGameState(): Boolean =
        dataStore.data.first()[KEY_GAME_STATE] != null

    suspend fun clearSavedGame() {
        dataStore.edit { it.remove(KEY_GAME_STATE) }
    }
}
