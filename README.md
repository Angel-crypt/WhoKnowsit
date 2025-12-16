# WhoKnowsIt

**WhoKnowsIt** es un videojuego de trivia desarrollado en **Android nativo**, enfocado en poner a prueba los conocimientos del jugador en distintas categorías mediante partidas configurables y un sistema de guardado.

## Descripción general
WhoKnowsIt es un juego de preguntas y respuestas para **un solo jugador (Single Player)**. El usuario puede configurar cada partida eligiendo:

* Categoría
* Dificultad
* Número de preguntas

El objetivo es responder correctamente la mayor cantidad de preguntas posibles y obtener el mejor puntaje.

## Jugabilidad

### Flujo del juego

1.  **Inicio:** Desde el menú principal, el jugador puede seleccionar **Nuevo Juego** o **Cargar Partida** (si existe una partida guardada).
2.  **Configuración:** Antes de comenzar, el jugador define la categoría, dificultad y cantidad de preguntas.
3.  **Partida:**
    *   Se muestra una pregunta a la vez.
    *   El jugador selecciona una respuesta.
    *   El juego muestra retroalimentación inmediata (correcto / incorrecto).
    *   Mientras no sea la última pregunta, el jugador puede guardar la partida.
4.  **Finalización:**
    *   Al responder la última pregunta, se muestra la pantalla de resultados.
    *  Si la partida fue cargada desde un guardado, este se elimina automáticamente al finalizar.

## Interfaz del juego

Pantallas principales que conforman la experiencia de usuario:

| Pantalla principal | Configuración de partida | Pantalla de pregunta |
|-------------------|-------------------------|----------------------|
| ![](docs/images/main_screen.png) | ![](docs/images/config_screen.png) | ![](docs/images/question_screen.png) |
| *Figura 1. Pantalla principal del juego.* | *Figura 2. Configuración de la partida.* | *Figura 3. Pantalla de pregunta.* |

| Resultado correcto | Resultado incorrecto |
|-------------------|----------------------|
| ![](docs/images/result_screen_correct.png) | ![](docs/images/result_screen_incorrect.png) |
| *Figura 4. Resultado al responder correctamente.* | *Figura 5. Resultado al responder incorrectamente.* |

## Videos de demostración

*   [Flujo completo del juego](docs/videos/full_gameplay.mp4)
*   [Guardar y cargar partida](docs/videos/save_load_demo.mp4)

## Controles

La interacción es completamente táctil:

*   **Responder:** Tocar una de las 4 opciones disponibles.
*   **Guardar partida:** Icono de guardar en la barra superior (no disponible en la última pregunta).

## Arquitectura

### Enfoque arquitectónico
El proyecto inició sin un arqitectura esrcita. Conforme creció la complejidad, me apoye de IA para definir la mejor arquitectura para el proyecto. Refactorizando hacia una **arquitectura por capas**, separando responsabilidades y mejorando la mantenibilidad del código.

### Capas del sistema

*   **UI (Presentation Layer):** Activities encargadas únicamente de renderizar el estado (`MainActivity`, `GameActivity`).
*   **Domain (Business Logic):** Lógica del juego y reglas (`QuestionManager`, `SaveManager`).
*   **Data (Data Layer):** Fuente de datos de preguntas (`DataSource`).
*   **Core:** Modelos y estados globales (`GameState`, `GameController`, `GameConfig`).

## Componentes principales

*   **`GameController`**:
    *  Orquestador central del juego.
    *  Controla el flujo de preguntas, validación de respuestas y finalización de la partida.

*   **`QuestionManager`**:
    *  Proporciona las preguntas según categoría y dificultad.
    *  Filtra el banco de preguntas disponible.

*   **`SaveManager`**:
    *  Gestiona la persistencia del estado del juego.
    *  Utiliza **DataStore** con serialización JSON.
    *  Funciones principales:
        *  `saveGameState()`: Guarda el estado actual del juego.
        *  `loadGameState()`: Carga el estado guardado.
        *  `clearSavedGame()`: Elimina el guardado actual.

*   **`SoundManager`**:
    *  Maneja los efectos de sonido del juego.
    *  Implementado con `MediaPlayer`.


## Manual de usuario

**Iniciar una partida nueva**
1.  Abrir la aplicación.
2.  Seleccionar **Nuevo Juego**.
3.  Configurar la partida.
4.  Responder las preguntas.
5.  Visualizar el resultado final.

**Guardar y cargar partida**
1.  **Guardar:** Durante la partida, tocar el icono de guardar antes de la última pregunta.
2.  **Cargar:** Si existe una partida guardada, el botón Cargar Partida estará disponible al iniciar la app.
3.  **Nota:** Al finalizar una partida cargada, el guardado se elimina automáticamente.

## Pruebas realizadas

*  Flujo completo de juego sin errores.
*  Guardado y recuperación correcta del progreso.
*  Validación de sonidos según la acción.
*  Ajustes visuales para mejorar la claridad de respuestas.

## Mejoras futuras

*  Soporte para múltiples partidas guardadas.
*  Pantalla de administración de guardados.
*  Carga de preguntas desde una API remota.
*  Modo multijugador.
*  Modo contrarreloj (Time Attack).

## Diagramas

```md
![Diagrama de flujo del estado del juego](docs/images/state_flow_diagram.png)
![Diagrama general de clases](docs/images/class_diagram.png)
```
