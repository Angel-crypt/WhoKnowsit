package com.example.whoknowsit.core
import kotlinx.serialization.Serializable

@Serializable
enum class Category(val displayName: String) {
    HISTORY("Historia"),
    SCIENCE("Ciencia"),
    POP_CULTURE("Cultura Pop"),
    FILM("Películas"),
    MUSIC("Música"),
    SPORTS("Deportes"),
    RANDOM("Aleatorio")
}