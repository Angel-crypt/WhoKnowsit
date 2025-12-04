package com.example.whoknowsit.data.models

import com.example.whoknowsit.core.Category
import com.example.whoknowsit.core.Difficulty

data class Question (
    val id: Int,
    val text: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val category: Category,
    val difficulty: Difficulty
)