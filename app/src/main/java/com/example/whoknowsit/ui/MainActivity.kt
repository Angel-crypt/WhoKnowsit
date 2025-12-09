package com.example.whoknowsit.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.whoknowsit.R
import com.example.whoknowsit.WhoKnowsItApplication
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val gameController by lazy {
        (application as WhoKnowsItApplication).gameController
    }
    private lateinit var loadGameButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        loadGameButton = findViewById(R.id.load_game_button)

        lifecycleScope.launch {
            val hasSavedGame = gameController.saveManager.isSavedGameState()
            loadGameButton.visibility =
                if (hasSavedGame) View.VISIBLE else View.GONE
        }
    }
}