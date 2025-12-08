package com.example.whoknowsit.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.whoknowsit.R
import com.example.whoknowsit.core.Category
import com.example.whoknowsit.core.Difficulty
import com.example.whoknowsit.data.LocalQuestionDataSource
import com.example.whoknowsit.domain.QuestionManager

class TestLogicActivity : AppCompatActivity() {
    
    private lateinit var questionManager: QuestionManager
    
    private lateinit var btnBack: Button
    private lateinit var btnCategory: Button
    private lateinit var btnDifficulty: Button
    private lateinit var btnQuantity: Button
    private lateinit var btnTest: Button
    private lateinit var tvResult: TextView
    
    private var selectedCategory = Category.HISTORY
    private var selectedDifficulty = Difficulty.EASY
    private var selectedQuantity = 5
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_test_logic)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        // Initialize views
        btnBack = findViewById(R.id.btn_back)
        btnCategory = findViewById(R.id.btn_category)
        btnDifficulty = findViewById(R.id.btn_difficulty)
        btnQuantity = findViewById(R.id.btn_quantity)
        btnTest = findViewById(R.id.btn_test)
        tvResult = findViewById(R.id.tv_result)
        
        // Back button
        btnBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
        
        // Initialize question manager
        val localDataSource = LocalQuestionDataSource(this)
        questionManager = QuestionManager(localDataSource)
        
        // Category selector
        btnCategory.text = selectedCategory.displayName
        btnCategory.setOnClickListener {
            val nextIndex = (selectedCategory.ordinal + 1) % Category.values().size
            selectedCategory = Category.entries.toTypedArray()[nextIndex]
            btnCategory.text = selectedCategory.displayName
        }
        
        // Difficulty selector
        btnDifficulty.text = selectedDifficulty.displayName
        btnDifficulty.setOnClickListener {
            val nextIndex = (selectedDifficulty.ordinal + 1) % Difficulty.values().size
            selectedDifficulty = Difficulty.entries.toTypedArray()[nextIndex]
            btnDifficulty.text = selectedDifficulty.displayName
        }
        
        // Quantity selector
        updateQuantityButton()
        btnQuantity.setOnClickListener {
            selectedQuantity = when(selectedQuantity) {
                5 -> 10
                10 -> 15
                else -> 5
            }
            updateQuantityButton()
        }
        
        // Test button
        btnTest.setOnClickListener {
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
                sb.append("Opt: ${q.options.joinToString(", ")}\n")
                sb.append("Corr: ${q.options[q.correctAnswerIndex]}\n")
            } else {
                sb.append("\nNO SE ENCONTRARON PREGUNTAS.\nRevisa tu JSON.")
            }
            
            tvResult.text = sb.toString()
        }
    }
    
    private fun updateQuantityButton() {
        btnQuantity.text = "$selectedQuantity Preguntas"
    }
}

