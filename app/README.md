# Who Knows It?

Who Knows It? es un juego de trivia interactivo para un solo jugador desarrollado en Android (Kotlin). Pon a prueba tus conocimientos en diversas categorías, acumula puntos y desafía diferentes niveles de dificultad.

## Características Principales

*   **Categorías Variadas:** Preguntas sobre Cine, Historia, Música, Cultura Pop, Ciencia y Deportes.
*   **Niveles de Dificultad:** Fácil, Medio y Difícil, cada uno con multiplicadores de puntuación para recompensar el riesgo.
*   **Sistema de Guardado:**
    *   Guarda tu progreso en cualquier momento durante la partida.
    *   Carga tu partida desde el menú principal.
    *   **Auto-borrado:** La partida guardada se elimina automáticamente al finalizar el juego (Game Over o Victoria) para mantener el desafío.
*   **Feedback Inmediato:** Retroalimentación visual y sonora al responder correcta e incorrectamente.
*   **Mascotas:** Personajes animados que reaccionan a tus resultados.

## Arquitectura del Proyecto

El proyecto sigue los principios de **Clean Architecture** para asegurar la escalabilidad y mantenibilidad:

*   **Core:** Contiene la lógica central del juego (`GameController`, `GameState`, `GameConfig`).
*   **Domain:** Contiene los casos de uso y gestores de lógica de negocio (`QuestionManager`, `ScoreManager`, `SoundManager`, `SaveManager`).
*   **Data:** Capa de acceso a datos (`LocalQuestionDataSource`, `QuestionDataSource`).
*   **UI:** Capa de presentación (Activities de Android).

## Tecnologías Utilizadas

*   **Lenguaje:** Kotlin
*   **Persistencia:** Jetpack DataStore (Preferences)
*   **Serialización:** Kotlinx Serialization (JSON)
*   **Concurrencia:** Kotlin Coroutines & Flow
*   **Diseño:** XML Layouts, Material Design Components

## Cómo Jugar

1.  **Nuevo Juego:** Selecciona "Juego Nuevo" en el menú principal.
2.  **Configuración:** Elige la categoría, la dificultad y el número de preguntas.
3.  **Jugar:** Responde las preguntas seleccionando una de las 4 opciones.
4.  **Guardar:** Puedes guardar tu partida usando el icono de disquete en la esquina superior derecha.
5.  **Cargar:** Si tienes una partida guardada, aparecerá el botón "Cargar Partida" en el menú principal.
6.  **Resultado:** Al finalizar, verás tu puntuación final y si has logrado la victoria.

## Estructura de Directorios

```
com.example.whoknowsit
├── core        # Lógica de estados y configuración
├── data        # Fuentes de datos (JSON, Repositorios)
├── domain      # Reglas de negocio (Managers)
└── ui          # Actividades y vistas
```
