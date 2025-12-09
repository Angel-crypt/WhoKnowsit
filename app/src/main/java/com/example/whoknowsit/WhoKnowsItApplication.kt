package com.example.whoknowsit

import android.app.Application
import com.example.whoknowsit.core.GameController

class WhoKnowsItApplication : Application() {
    val gameController: GameController by lazy {
        GameController(applicationContext)
    }
    
    companion object {
        @JvmStatic
        fun getInstance(context: android.content.Context): WhoKnowsItApplication {
            return context.applicationContext as WhoKnowsItApplication
        }
    }
}


