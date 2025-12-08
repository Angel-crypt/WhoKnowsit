package com.example.whoknowsit.domain

import android.content.Context
import android.media.SoundPool
import com.example.whoknowsit.R

class SoundManager(context: Context) {
    private val soundPool: SoundPool =
        SoundPool.Builder().setMaxStreams(4).build()

    private val correctSound: Int
    private val wrongSound: Int
    private val winSound: Int
    private val loseSound: Int

    init {
        correctSound = soundPool.load(context, R.raw.correct_sound, 1)
        wrongSound = soundPool.load(context, R.raw.wrong_sound, 1)
        winSound = soundPool.load(context, R.raw.win_sound, 1)
        loseSound = soundPool.load(context, R.raw.lose_sound, 1)
    }

    fun playCorrect() = playSound(correctSound)
    fun playWrong() = playSound(wrongSound)
    fun playWin() = playSound(winSound)
    fun playLose() = playSound(loseSound)

    private fun playSound(soundId: Int) {
        android.util.Log.d("SoundManager", "Playing sound with ID: $soundId")
        soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
    }

    fun release() {
        soundPool.release()
    }
}