package com.example.whoknowsit.model

data class Challenge(
    val id: Int,
    val text: String,
    val difficulty: Int,
    val votes: MutableMap<Int, Boolean> = mutableMapOf() // Player ID to vote status
) {
    fun isSuccessful(): Boolean {
        return votes.values.all { it }
    }
}