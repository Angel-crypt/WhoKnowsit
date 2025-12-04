package com.example.whoknowsit.domain

import com.example.whoknowsit.core.Category
import com.example.whoknowsit.core.Difficulty
import com.example.whoknowsit.data.QuestionDataSource
import com.example.whoknowsit.data.models.Question

class QuestionManager(private val dataSource: QuestionDataSource) {

    fun getQuestionsForCategory(
        category: Category,
        difficulty: Difficulty,
        totalQuestions: Int
    ): List<Question> {
        val filteredQuestions = if (category == Category.RANDOM) {
            dataSource.loadQuestions().filter { it.difficulty == difficulty }
        } else {
            dataSource.loadQuestions().filter {
                it.category == category && it.difficulty == difficulty
            }
        }

        return filteredQuestions.shuffled().take(totalQuestions)
    }

    fun shuffleQuestions(questions: List<Question>): List<Question> = questions.shuffled()
}
