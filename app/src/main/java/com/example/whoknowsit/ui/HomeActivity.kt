package com.example.whoknowsit.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.whoknowsit.R
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity() {
    
    private var isFabMenuOpen = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        val fabMain = findViewById<FloatingActionButton>(R.id.fab_main)
        val fabAudio = findViewById<ExtendedFloatingActionButton>(R.id.fab_audio)
        val fabLogic = findViewById<ExtendedFloatingActionButton>(R.id.fab_logic)
        val fabOverlay = findViewById<View>(R.id.fab_overlay)

        val newGameBtn = findViewById<View>(R.id.newGame_btn)

        newGameBtn.setOnClickListener {
            val intent = Intent(this, NewGameSetupActivity::class.java)
            startActivity(intent)
        }

        fabMain.setOnClickListener {
            toggleFabMenu()
        }

        fabAudio.setOnClickListener {
            val intent = Intent(this, TestAudioActivity::class.java)
            startActivity(intent)
            closeFabMenu()
        }

        fabLogic.setOnClickListener {
            val intent = Intent(this, TestLogicActivity::class.java)
            startActivity(intent)
            closeFabMenu()
        }
        
        fabOverlay.setOnClickListener {
            closeFabMenu()
        }
    }
    
    private fun toggleFabMenu() {
        if (isFabMenuOpen) {
            closeFabMenu()
        } else {
            openFabMenu()
        }
    }
    
    private fun openFabMenu() {
        isFabMenuOpen = true
        val fabAudio = findViewById<ExtendedFloatingActionButton>(R.id.fab_audio)
        val fabLogic = findViewById<ExtendedFloatingActionButton>(R.id.fab_logic)
        val fabOverlay = findViewById<View>(R.id.fab_overlay)
        
        fabOverlay.visibility = View.VISIBLE
        fabOverlay.alpha = 0f
        fabOverlay.animate().alpha(1f).setDuration(200).start()
        
        fabAudio.visibility = View.VISIBLE
        fabAudio.alpha = 0f
        fabAudio.translationY = 0f
        fabAudio.animate()
            .alpha(1f)
            .translationY(-(fabAudio.height + 16).toFloat())
            .setDuration(200)
            .start()
        
        fabLogic.visibility = View.VISIBLE
        fabLogic.alpha = 0f
        fabLogic.translationY = 0f
        fabLogic.animate()
            .alpha(1f)
            .translationY(-(fabLogic.height + 16).toFloat())
            .setDuration(200)
            .start()
    }
    
    private fun closeFabMenu() {
        isFabMenuOpen = false
        val fabAudio = findViewById<ExtendedFloatingActionButton>(R.id.fab_audio)
        val fabLogic = findViewById<ExtendedFloatingActionButton>(R.id.fab_logic)
        val fabOverlay = findViewById<View>(R.id.fab_overlay)
        
        fabOverlay.animate().alpha(0f).setDuration(200).withEndAction {
            fabOverlay.visibility = View.GONE
        }.start()
        
        fabAudio.animate()
            .alpha(0f)
            .translationY(0f)
            .setDuration(200)
            .withEndAction {
                fabAudio.visibility = View.GONE
            }.start()
        
        fabLogic.animate()
            .alpha(0f)
            .translationY(0f)
            .setDuration(200)
            .withEndAction {
                fabLogic.visibility = View.GONE
            }.start()
    }
}
