package com.example.whoknowsit.domain

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import com.example.whoknowsit.R

class SoundManager(private val context: Context) {

    fun playCorrect() = play(R.raw.correct_sound)
    fun playWrong() = play(R.raw.wrong_sound)
    fun playWin() = play(R.raw.win_sound)
    fun playLose() = play(R.raw.lose_sound)

    private fun play(resId: Int) {
        val mediaPlayer = MediaPlayer.create(context, resId)
        if (mediaPlayer == null) {
            return
        }
        mediaPlayer.setOnCompletionListener {
            it.release()
        }
        mediaPlayer.start()
    }
}