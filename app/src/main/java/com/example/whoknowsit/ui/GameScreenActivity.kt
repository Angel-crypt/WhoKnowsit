package com.example.whoknowsit.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.whoknowsit.WhoKnowsItApplication


class GameScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

    }

    override fun onDestroy() {
        super.onDestroy()
        val gameController = (application as WhoKnowsItApplication).gameController

        gameController.soundManager.release()
    }
}