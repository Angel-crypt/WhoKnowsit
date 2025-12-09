package com.example.whoknowsit.data

import android.content.Context
import com.example.whoknowsit.core.enums.Category
import com.example.whoknowsit.core.enums.Difficulty
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

    override fun loadQuestions(): List<Question> =
        jsonFiles
            .flatMap { file -> parseJsonFile("questions/$file") }

    private fun parseJsonFile(path: String): List<Question> {
        val raw = loadJsonFromAssets(path)
        val jsonArray = JSONArray(raw)

        return List(jsonArray.length()) { index ->
            parseQuestion(jsonArray.getJSONObject(index))
        }
    }

    private fun loadJsonFromAssets(path: String): String =
        context.assets.open(path).bufferedReader().use { it.readText() }

    private fun parseQuestion(obj: JSONObject): Question {
        val optionsArray = obj.getJSONArray("options")
        val options = List(optionsArray.length()) { i -> optionsArray.getString(i) }

        val category = Category.valueOf(obj.getString("category").uppercase())

        val difficultyText = obj.getString("difficulty").uppercase()
        val difficulty = when (difficultyText) {
            "EASY" -> Difficulty.EASY
            "MEDIUM" -> Difficulty.MEDIUM
            "HARD", "DIFFICULT" -> Difficulty.HARD
            else -> throw IllegalArgumentException("Dificultad inválida: $difficultyText")
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
