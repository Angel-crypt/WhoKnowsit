package com.example.whoknowsit.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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
    private lateinit var newGameButton: MaterialButton

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

        newGameButton = findViewById(R.id.new_game_button)
        newGameButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}