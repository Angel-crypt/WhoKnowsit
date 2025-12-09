package com.example.whoknowsit.core.enums
import kotlinx.serialization.Serializable

@Serializable
enum class Difficulty(val displayName: String, val multiplier: Int) {
    EASY("Fácil", 1),
    MEDIUM("Medio", 2),
    HARD("Difícil", 3)
}

