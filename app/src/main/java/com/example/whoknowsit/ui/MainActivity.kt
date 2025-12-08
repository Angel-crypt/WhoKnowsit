package com.example.whoknowsit.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whoknowsit.core.Category
import com.example.whoknowsit.core.Difficulty
import com.example.whoknowsit.data.LocalQuestionDataSource
import com.example.whoknowsit.domain.QuestionManager
import com.example.whoknowsit.domain.SoundManager
import com.example.whoknowsit.ui.theme.WhoKnowsItTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val localDataSource = LocalQuestionDataSource(this)
        val questionManager = QuestionManager(localDataSource)
        val soundManager = SoundManager(this)

        setContent {
            WhoKnowsItTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TestDashboard(
                        modifier = Modifier.padding(innerPadding),
                        soundManager = soundManager,
                        questionManager = questionManager
                    )
                }
            }
        }
    }
}

@Composable
fun TestDashboard(
    modifier: Modifier = Modifier,
    soundManager: SoundManager,
    questionManager: QuestionManager
) {
    var selectedCategory by remember { mutableStateOf(Category.HISTORY) }
    var selectedDifficulty by remember { mutableStateOf(Difficulty.EASY) }
    var selectedQuantity by remember { mutableStateOf(5) }

    var logResult by remember { mutableStateOf("Configura los filtros y presiona PROBAR.") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Pruebas de Audio", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { soundManager.playCorrect() }, colors = ButtonDefaults.buttonColors(Color(0xFF4CAF50))) { Text("CORRECT") }
            Button(onClick = { soundManager.playWrong() }, colors = ButtonDefaults.buttonColors(Color(0xFFF44336))) { Text("WRONG") }
            Button(onClick = { soundManager.playWin() }, colors = ButtonDefaults.buttonColors(Color(0xFFFFC107))) { Text("WIN") }
            Button(onClick = { soundManager.playLose() }, colors = ButtonDefaults.buttonColors(Color(0xFF9E9E9E))) { Text("LOSE") }
        }

        HorizontalDivider()
        Text("Pruebas de Lógica", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)

        OpcionSelector(
            titulo = "Categoría:",
            valorActual = selectedCategory.displayName,
            onNext = {
                val nextIndex = (selectedCategory.ordinal + 1) % Category.values().size
                selectedCategory = Category.entries.toTypedArray()[nextIndex]
            }
        )

        OpcionSelector(
            titulo = "Dificultad:",
            valorActual = selectedDifficulty.displayName,
            onNext = {
                val nextIndex = (selectedDifficulty.ordinal + 1) % Difficulty.values().size
                selectedDifficulty = Difficulty.entries.toTypedArray()[nextIndex]
            }
        )

        OpcionSelector(
            titulo = "Cantidad:",
            valorActual = "$selectedQuantity Preguntas",
            onNext = {
                selectedQuantity = when(selectedQuantity) {
                    5 -> 10
                    10 -> 15
                    else -> 5
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val questions = questionManager.getQuestionsForCategory(
                    category = selectedCategory,
                    difficulty = selectedDifficulty,
                    totalQuestions = selectedQuantity
                )

                val sb = StringBuilder()
                sb.append("RESULTADO:\n")
                sb.append("Solicitado: ${selectedQuantity} de ${selectedCategory.displayName} (${selectedDifficulty.displayName})\n")
                sb.append("Encontradas: ${questions.size}\n")

                if (questions.isNotEmpty()) {
                    val q = questions.first()

                    sb.append("\nEjemplo (1ra):\n")
                    sb.append("${q.text}\n")
                    sb.append("Cat: ${q.category.displayName}\n")
                    sb.append("Dif: ${q.difficulty.displayName}\n")
                    sb.append("Opt: ${q.options.joinToString(", ")}")

                } else {
                    sb.append("\nNO SE ENCONTRARON PREGUNTAS.\nRevisa tu JSON.")
                }

                logResult = sb.toString()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color(0xFF6200EE))
        ) {
            Text("PROBAR")
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF0F0F0), RoundedCornerShape(8.dp))
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Text(
                text = logResult,
                fontSize = 14.sp,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
            )
        }
    }
}

@Composable
fun OpcionSelector(titulo: String, valorActual: String, onNext: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = titulo, fontWeight = FontWeight.Medium)
        Button(
            onClick = onNext,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = valorActual, color = MaterialTheme.colorScheme.onSecondaryContainer)
        }
    }
}