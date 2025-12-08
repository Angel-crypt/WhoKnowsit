package com.example.whoknowsit.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.whoknowsit.R
import com.example.whoknowsit.domain.SoundManager

class TestAudioActivity : AppCompatActivity() {
    
    private lateinit var soundManager: SoundManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_test_audio)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        // Back button
        findViewById<Button>(R.id.btn_back).setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
        
        // Initialize sound manager
        soundManager = SoundManager(this)
        
        // Audio test buttons
        findViewById<Button>(R.id.btn_correct).setOnClickListener {
            soundManager.playCorrect()
        }
        findViewById<Button>(R.id.btn_wrong).setOnClickListener {
            soundManager.playWrong()
        }
        findViewById<Button>(R.id.btn_win).setOnClickListener {
            soundManager.playWin()
        }
        findViewById<Button>(R.id.btn_lose).setOnClickListener {
            soundManager.playLose()
        }
    }
}

