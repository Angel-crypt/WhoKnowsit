package com.example.whoknowsit.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.whoknowsit.R
import com.google.android.material.button.MaterialButton

class QuestionActivity : AppCompatActivity() {

    private lateinit var optionViews: List<MaterialButton>
    private var selectedOptionIndex: Int? = null

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
    }

    private fun initializeViews() {
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
    }

    private fun updateSelection(selectedView: View) {
        optionViews.forEach { view ->
            view.isSelected = (view == selectedView)
        }
    }
}