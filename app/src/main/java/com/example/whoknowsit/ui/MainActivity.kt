package com.example.whoknowsit.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.whoknowsit.core.Category
import com.example.whoknowsit.core.Difficulty
import com.example.whoknowsit.data.LocalQuestionDataSource
import com.example.whoknowsit.domain.QuestionManager
import com.example.whoknowsit.ui.theme.WhoKnowsItTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 1️⃣ Crear DataSource
        val localDataSource = LocalQuestionDataSource(this)

        // 2️⃣ Crear QuestionManager
        val questionManager = QuestionManager(localDataSource)

        // 3️⃣ Obtener preguntas filtradas
        val selectedCategory = Category.HISTORY
        val selectedDifficulty = Difficulty.HARD
        val totalQuestions = 5

        val questions = questionManager.getQuestionsForCategory(
            category = selectedCategory,
            difficulty = selectedDifficulty,
            totalQuestions = totalQuestions
        )

        android.util.Log.d("TEST_JSON", "Total preguntas cargadas: ${questions.size}")
        android.util.Log.d("TEST_JSON", "Primera pregunta: ${questions.firstOrNull()}")


        setContent {
            WhoKnowsItTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WhoKnowsItTheme {
        Greeting("Android")
    }
}