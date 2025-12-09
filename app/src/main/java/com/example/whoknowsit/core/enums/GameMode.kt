package com.example.whoknowsit.core.enums
import kotlinx.serialization.Serializable

@Serializable
enum class GameMode(val displayName: String) {
    NEW("Nuevo juego"),
    CONTINUE("Continuar")
}

