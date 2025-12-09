package com.example.whoknowsit

import android.app.Application
import com.example.whoknowsit.core.GameController

class WhoKnowsItApplication : Application() {
    val gameController: GameController by lazy {
        GameController(applicationContext)
    }
}
