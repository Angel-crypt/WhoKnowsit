package com.example.whoknowsit.domain

import android.content.Context
import android.media.SoundPool
import com.example.whoknowsit.R

class SoundManager(context: Context) {

    private val soundPool = SoundPool.Builder()
        .setMaxStreams(4)
        .build()

    private val sounds = mapOf(
        "correct" to soundPool.load(context, R.raw.correct_sound, 1),
        "wrong"   to soundPool.load(context, R.raw.wrong_sound, 1),
        "win"     to soundPool.load(context, R.raw.win_sound, 1),
        "lose"    to soundPool.load(context, R.raw.lose_sound, 1)
    )

    fun playCorrect() = play("correct")
    fun playWrong() = play("wrong")
    fun playWin() = play("win")
    fun playLose() = play("lose")

    private fun play(key: String) {
        val soundId = sounds[key] ?: return
        android.util.Log.d("SoundManager", "Playing sound: $key ($soundId)")
        soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
    }

    fun release() = soundPool.release()
}