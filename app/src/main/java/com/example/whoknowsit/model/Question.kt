package com.example.whoknowsit.model

data class Question (
    val id: Int,
    val text: String,
    val difficulty: Int,
    val targetPlayerId: Int? = null,
    val correctAnswer: String
)
