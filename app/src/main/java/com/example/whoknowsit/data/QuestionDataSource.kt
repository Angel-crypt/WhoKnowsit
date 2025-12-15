package com.example.whoknowsit.data

import com.example.whoknowsit.data.models.Question

/**
 * Interfaz para definir una fuente de datos de preguntas.
 */
interface QuestionDataSource {
    /**
     * Carga todas las preguntas disponibles.
     * @return Lista de preguntas cargadas.
     */
    fun loadQuestions(): List<Question>
}