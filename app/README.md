# Documentación del Videojuego WhoKnowsIt

## 1. Introducción

Esta documentación describe el desarrollo, funcionamiento y características técnicas de **WhoKnowsIt**, un videojuego de preguntas y respuestas desarrollado en Android nativo. El propósito de este proyecto fue crear una experiencia de juego completa, desde la configuración de una partida hasta la gestión del estado del juego, aplicando buenas prácticas de desarrollo móvil y adaptando la arquitectura según las necesidades surgieron.

## 2. Descripción del juego

**WhoKnowsIt** es un juego de trivia para un solo jugador ("Single Player") donde el objetivo es responder correctamente cuestionarios generados según las preferencias del usuario.

### Jugabilidad y Aspecto del Juego

#### Flujo real del juego

1.  **Inicio:** Desde el menú principal, el jugador puede elegir entre "Nuevo Juego" o, si existe una partida previa, "Cargar Partida".
2.  **Configuración:** Al iniciar una nueva partida, se configuran parámetros clave: Categoría, Dificultad y Número de Preguntas.
3.  **Partida:**
    *   Se presenta una pregunta a la vez.
    *   El usuario selecciona una respuesta y recibe retroalimentación inmediata (acierto/error).
    *   Si **NO** es la última pregunta, aparece un botón para **Guardar Partida**, lo que permite pausar y guardar el progreso actual (pregunta actual y puntaje).
4.  **Finalización:**
    *   Al responder la última pregunta, el juego termina y muestra la pantalla de resultados.
    *   **Importante:** Si se estaba jugando una partida guardada, al llegar al final y ver los resultados, la partida guardada se elimina automáticamente del sistema.

#### Aspecto visual del juego

A continuación se muestran las pantallas principales que componen la experiencia de usuario.

| Pantalla principal | Configuración de partida | Pantalla de pregunta |
|-------------------|-------------------------|----------------------|
| ![](docs/images/main_screen.png) | ![](docs/images/config_screen.png) | ![](docs/images/question_screen.png) |
| *Figura 1. Pantalla principal del juego.* | *Figura 2. Configuración de la partida.* | *Figura 3. Pantalla de pregunta.* |

| Resultado correcto | Resultado incorrecto |
|-------------------|----------------------|
| ![](docs/images/result_screen_correct.png) | ![](docs/images/result_screen_incorrect.png) |
| *Figura 4. Resultado al responder correctamente.* | *Figura 5. Resultado al responder incorrectamente.* |


#### Videos de jugabilidad

Para ver el funcionamiento en tiempo real, consulta los siguientes videos demostrativos:

*   [Ver video – Flujo completo del juego](docs/videos/full_gameplay.mp4)
*   [Ver video – Guardar y cargar partida](docs/videos/save_load_demo.mp4)

## 3. Controles

La interfaz está diseñada para ser intuitiva y totalmente táctil:

*   **Selección de respuesta:** Tocar ("Tap") sobre cualquiera de los 4 botones de opción.
*   **Guardado:** Botón de disquete (Guardar) ubicado en la barra superior, disponible durante el juego (menos en la última pregunta).

## 4. Arquitectura del sistema

### Decisión de arquitectura
El proyecto no comenzó con una arquitectura estricta. Inicialmente, gran parte de la lógica residía en las mismas actividades. Conforme el proyecto creció y con el apoyo de herramientas de IA, se refactorizó el código hacia una arquitectura por capas basada en responsabilidades. Esto se eligió para desacoplar la lógica del juego de la interfaz gráfica, facilitando la detección de errores y cambios futuros.

### Arquitectura utilizada
El sistema se organiza en las siguientes capas:

*   **UI (Presentation Layer):** Activities (`MainActivity`, `GameActivity`) que solo renderizan el estado.
*   **Domain (Business Logic):** Managers (`QuestionManager`, `SaveManager`) que ejecutan las reglas del negocio.
*   **Data (Data Layer):** Origen de los datos (DataSource).
*   **Core:** Clases de soporte y definiciones de estado (`GameState`).

### Clases principales del sistema

*   **`GameController`**:
    *   *Responsabilidad:* Orquestador central. Maneja el ciclo de vida de una partida activa.
    *   *Lógica:* Valida respuestas, invoca el cambio de pregunta y determina cuándo termina el juego.

*   **`QuestionManager`**:
    *   *Responsabilidad:* Proveer las preguntas.
    *   *Métodos:* Filtra el banco de preguntas según la categoría y dificultad seleccionada.

*   **`SaveManager`**:
    *   *Responsabilidad:* Persistencia del juego.
    *   *Tecnología:* Utiliza **DataStore** y Serialización JSON.
    *   *Métodos:* `saveGameState()` (guarda estado actual), `loadGameState` (recupera flujo), `clearSavedGame()` (borra al terminar).

*   **`SoundManager`**:
    *   *Responsabilidad:* Reproducción de efectos de sonido.
    *   *Tecnología:* `MediaPlayer`.


## 5. Manual de usuario

**Cómo jugar una partida nueva:**
1.  Abre la aplicación.
2.  Toca "Nuevo Juego".
3.  Selecciona tus preferencias (Ej. Cine, Medio, 10 preguntas) y toca "Comenzar".
4.  Responde tocando la opción que creas correcta.
5.  Al final, verás tu resultado.

**Cómo guardar y cargar:**
1.  **Guardar:** Mientras juegas (antes de la última pregunta), toca el icono de guardar en la parte superior. Aparecerá un mensaje confirmando el guardado. Puedes salir de la app con seguridad.
2.  **Cargar:** Al abrir la app, si hay una partida guardada, verás activo el botón "Cargar Partida". Tócalo para retomar exactamente donde te quedaste.
3.  **Nota:** Si terminas una partida cargada, esta se borrará automáticamente.

## 6. Pruebas realizadas

Se realizaron las siguientes pruebas funcionales reales:

*   **Prueba de Flujo:** Completar partidas de principio a fin sin cierres inesperados.
*   **Prueba de Guardado:** Guardar en la pregunta 3, cerrar la app, cargar y verificar que se retoma en la pregunta 3 con el puntaje correcto.
*   **Prueba de Sonidos:** Verificación auditiva de que los sonidos corresponden a la acción (acierto/error) y corrección de superposición de audios.
*   **Prueba de Interfaz:** Ajuste de colores en botones de respuesta para asegurar que el usuario distinga claramente si acertó o falló.

## 7. Conclusiones y mejoras futuras

El proyecto es funcional y demuestra el uso efectivo de componentes modernos de Android como DataStore. Sin embargo, hay margen de mejora:

*   **Guardar múltiples partidas:** Actualmente solo se soporta un "slot" de guardado. Sería ideal permitir varios.
*   **Administración:** Una pantalla para ver y borrar partidas guardadas manualmente.
*   **Más preguntas:** Cargar preguntas desde una API remota.
*   **Modo Multijugador:** Competencia local o en red.
*   **Time Attack:** Modo de juego contra reloj.

---

## 8. Diagramas

```md
![Diagrama de flujo del estado del juego](docs/images/state_flow_diagram.png)
![Diagrama general de clases](docs/images/class_diagram.png)
```
