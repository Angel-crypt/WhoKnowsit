package com.example.whoknowsit

import android.app.Application
import com.example.whoknowsit.core.GameController

/**
 * Clase de aplicación principal. Inicializa el controlador global del juego.
 */
class WhoKnowsItApplication : Application() {
    val gameController: GameController by lazy {
        GameController(applicationContext)
    }
}
