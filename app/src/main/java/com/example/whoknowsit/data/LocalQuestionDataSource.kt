package com.example.whoknowsit.data

import com.example.whoknowsit.data.models.Question

class LocalQuestionDataSource : QuestionDataSource {
    private val questionList: List<Question> = emptyList()

    override fun loadQuestions(): List<Question> {
        return questionList
    }

}