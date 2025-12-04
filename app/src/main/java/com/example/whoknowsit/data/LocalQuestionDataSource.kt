package com.example.whoknowsit.data

import android.content.Context
import com.example.whoknowsit.core.Category
import com.example.whoknowsit.core.Difficulty
import com.example.whoknowsit.data.models.Question
import org.json.JSONArray
import org.json.JSONObject

class LocalQuestionDataSource(private val context: Context) : QuestionDataSource {
    private val jsonFiles = listOf(
        "films.json",
        "history.json",
        "music.json",
        "pop_culture.json",
        "science.json",
        "sports.json"
    )

    override fun loadQuestions(): List<Question> {
        val questionList = mutableListOf<Question>()

        jsonFiles.forEach { fileName ->
            val raw = loadJsonFromAssets("questions/$fileName")
            val jsonArray = JSONArray(raw)

            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val question = parseQuestion(obj)
                questionList.add(question)
            }
        }

        return questionList
    }

    private fun loadJsonFromAssets(path: String): String {
        return context.assets.open(path).bufferedReader().use { it.readText() }
    }

    private fun parseQuestion(obj: JSONObject): Question {
        val options = List(obj.getJSONArray("options").length()) {
            obj.getJSONArray("options").getString(it)
        }

        val category = Category.valueOf(obj.getString("category").uppercase())

        val difficulty = when (obj.getString("difficulty").uppercase()) {
            "EASY" -> Difficulty.EASY
            "MEDIUM" -> Difficulty.MEDIUM
            "HARD", "DIFFICULT" -> Difficulty.HARD
            else -> throw IllegalArgumentException("Dificultad inválida: ${obj.getString("difficulty")}")
        }

        return Question(
            id = obj.getInt("id"),
            text = obj.getString("question"),
            options = options,
            correctAnswerIndex = obj.getInt("correctIndex"),
            category = category,
            difficulty = difficulty
        )
    }
}