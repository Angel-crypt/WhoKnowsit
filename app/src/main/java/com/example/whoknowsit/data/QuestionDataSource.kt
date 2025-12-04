package com.example.whoknowsit.data

import com.example.whoknowsit.data.models.Question

interface QuestionDataSource {
    fun loadQuestions(): List<Question>
}