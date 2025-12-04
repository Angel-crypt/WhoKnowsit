package com.example.whoknowsit.domain

import com.example.whoknowsit.core.Category
import com.example.whoknowsit.core.Difficulty
import com.example.whoknowsit.data.QuestionDataSource
import com.example.whoknowsit.data.models.Question

class QuestionManager (private val dataSource: QuestionDataSource) {
    fun getQuestionsForCategory(
        category: Category,
        difficulty: Difficulty,
        totalQuestions: Int
    ){}

    fun shuffleQuestions(
        questions: List<Question>
    ){}
}