package com.example.whoknowsit.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.whoknowsit.R
import com.example.whoknowsit.WhoKnowsItApplication
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class QuestionActivity : AppCompatActivity() {

    private lateinit var questionTextView: TextView
    private lateinit var counterTextView: TextView
    private lateinit var optionViews: List<MaterialButton>
    private lateinit var saveGameBtn: ImageButton
    private var selectedOptionIndex: Int? = null
    private var shuffledIndexes: List<Int> = emptyList()

    private val gameController by lazy {
        (application as WhoKnowsItApplication).gameController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_question)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initializeViews()
        setupListeners()

        lifecycleScope.launch {
            val canSaveGame = gameController.gameState.currentQuestionIndex < gameController.gameState.questions.size - 1
            saveGameBtn.visibility =
                if (canSaveGame) View.VISIBLE else View.GONE
        }
        
        gameController.gameState.currentQuestion?.let { question ->
            setQuestionText(question.text)
            setOptions(question.options)
            setCounter(
                gameController.gameState.currentQuestionIndex + 1,
                gameController.gameState.questions.size
            )
        }
    }

    private fun initializeViews() {
        questionTextView = findViewById(R.id.question_text_view)
        counterTextView = findViewById(R.id.counter_question)
        saveGameBtn = findViewById(R.id.save_game_button)
        
        optionViews = listOf(
            findViewById(R.id.opt_1),
            findViewById(R.id.opt_2),
            findViewById(R.id.opt_3),
            findViewById(R.id.opt_4)
        )
    }

    private fun setupListeners() {
        optionViews.forEachIndexed { index, button ->
            button.setOnClickListener {
                selectedOptionIndex = index
                updateSelection(button)
            }
        }

        saveGameBtn.setOnClickListener {
            lifecycleScope.launch {
                gameController.saveManager.saveGameState(gameController.gameState)
                Toast.makeText(this@QuestionActivity, "Partida guardada correctamente", Toast.LENGTH_SHORT).show()
                val intent = android.content.Intent(this@QuestionActivity, com.example.whoknowsit.ui.MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun updateSelection(selectedView: View) {
        optionViews.forEach { view ->
            view.isSelected = (view == selectedView)
        }
        val selectedVisualIndex = optionViews.indexOf(selectedView)
        if (selectedVisualIndex != -1 && selectedVisualIndex < shuffledIndexes.size) {
            val originalIndex = shuffledIndexes[selectedVisualIndex]
            lifecycleScope.launch {
                gameController.handleAnswer(originalIndex, this@QuestionActivity)
            }
        }
    }

    fun setQuestionText(text: String) {
        questionTextView.text = text
    }

    fun setOptions(options: List<String>) {
        shuffledIndexes = options.indices.toList().shuffled()
        shuffledIndexes.forEachIndexed { visualIndex, originalIndex ->
            if (visualIndex < optionViews.size) {
                optionViews[visualIndex].text = options[originalIndex]
            }
        }
    }

    fun setCounter(current: Int, total: Int) {
        counterTextView.text = "$current/$total"
    }
}