package com.example.whoknowsit.domain

import com.example.whoknowsit.core.enums.Category
import com.example.whoknowsit.core.enums.Difficulty
import com.example.whoknowsit.data.QuestionDataSource
import com.example.whoknowsit.data.models.Question

/**
 * Gestor de preguntas encargado de filtrar y suministrar preguntas del repositorio.
 */
class QuestionManager(private val dataSource: QuestionDataSource) {
    /**
     * Obtiene una lista de preguntas aleatorias filtradas por categoría y dificultad.
     * @param category Categoría deseada, o RANDOM para todas.
     * @param difficulty Dificultad deseada.
     * @param totalQuestions Cantidad de preguntas a devolver.
     * @return Lista de preguntas barajadas.
     */
    fun getQuestionsForCategory(
        category: Category,
        difficulty: Difficulty,
        totalQuestions: Int
    ): List<Question> {

        val questions = dataSource.loadQuestions()

        val filtered = questions.filter { q ->
            q.difficulty == difficulty &&
                    (category == Category.RANDOM || q.category == category)
        }

        return filtered.shuffled().take(totalQuestions)
    }
}
