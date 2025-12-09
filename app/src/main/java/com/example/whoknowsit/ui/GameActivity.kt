package com.example.whoknowsit.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.whoknowsit.R
import com.example.whoknowsit.WhoKnowsItApplication
import com.example.whoknowsit.core.GameConfig
import com.example.whoknowsit.core.enums.Category
import com.example.whoknowsit.core.enums.Difficulty
import com.example.whoknowsit.core.enums.GameMode

class GameActivity : AppCompatActivity() {

    private var selectedCategory: Category? = null
    private var selectedDifficulty: Difficulty? = null
    private var selectedQuestions: Int? = null

    private lateinit var categoryViews: Map<Category, View>
    private lateinit var difficultyViews: Map<Difficulty, View>
    private lateinit var quantityViews: Map<Int, View>

    private val gameController by lazy {
        (application as WhoKnowsItApplication).gameController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)

        initializeViews()
        setupListeners()
    }

    private fun initializeViews() {
        categoryViews = mapOf(
            Category.FILM to findViewById(R.id.cat_film),
            Category.HISTORY to findViewById(R.id.cat_history),
            Category.MUSIC to findViewById(R.id.cat_music),
            Category.POP_CULTURE to findViewById(R.id.cat_pop),
            Category.RANDOM to findViewById(R.id.cat_random),
            Category.SCIENCE to findViewById(R.id.cat_science),
            Category.SPORTS to findViewById(R.id.cat_sports)
        )

        difficultyViews = mapOf(
            Difficulty.EASY to findViewById(R.id.diff_easy),
            Difficulty.MEDIUM to findViewById(R.id.diff_medium),
            Difficulty.HARD to findViewById(R.id.diff_hard)
        )

        quantityViews = mapOf(
            5 to findViewById(R.id.qty_5),
            10 to findViewById(R.id.qty_10),
            20 to findViewById(R.id.qty_20)
        )
    }

    private fun setupListeners() {

        categoryViews.forEach { (category, view) ->
            view.setOnClickListener {
                selectedCategory = category
                applySelection(categoryViews, category)
            }
        }

        difficultyViews.forEach { (difficulty, view) ->
            view.setOnClickListener {
                selectedDifficulty = difficulty
                applySelection(difficultyViews, difficulty)
            }
        }

        quantityViews.forEach { (qty, view) ->
            view.setOnClickListener {
                selectedQuestions = qty
                applySelection(quantityViews, qty)
            }
        }

        findViewById<View>(R.id.play_button).setOnClickListener {
            validateAndPlay()
        }
    }

    private fun <T> applySelection(map: Map<T, View>, selected: T) {
        map.forEach { (key, view) ->
            view.isSelected = (key == selected)
        }
    }

    private fun validateAndPlay() {
        if (selectedCategory != null && selectedDifficulty != null && selectedQuestions != null) {
            val config = GameConfig(
                totalQuestions = selectedQuestions!!,
                category = selectedCategory!!,
                difficulty = selectedDifficulty!!,
                gameMode = GameMode.NEW
            )

            gameController.startNewGame(config)

            val intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Por favor selecciona todas las opciones", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        gameController.soundManager.release()
    }
}
