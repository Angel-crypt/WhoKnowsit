package com.example.whoknowsit.core

enum class GameMode(val displayName: String) {
    NEW("Nuevo juego"),
    CONTINUE("Continuar");

    fun isNew() = this == NEW
    fun isContinue() = this == CONTINUE
}
