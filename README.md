# Who Knows it?

**Descripción:**

Who Knows it? es un juego de trivia para un solo jugador inspirado en clásicos como *Trivia Crack* y *Preguntados*. El jugador responde preguntas de opción múltiple en distintas categorías (Historia, Ciencia, Cultura Pop, Cine, Deportes), acumula aciertos y puede continuar una partida previa gracias al sistema de guardado automático. Está diseñado para ser ligero, rápido y fácil de expandir mediante archivos JSON.

**Objetivo general:**

Ofrecer una experiencia sencilla, divertida y educativa, donde el jugador pueda poner a prueba sus conocimientos a través de preguntas aleatorias organizadas por categorías.

---

# **Objetivos específicos**

* Brindar un sistema de trivia directo y dinámico basado en selección múltiple.
* Permitir partidas nuevas con categorías personalizadas.
* Proveer un sistema de guardado que permita continuar la partida en cualquier momento.
* Garantizar un flujo de juego simple y accesible, sin elementos competitivos o multijugador.
* Facilitar escalabilidad mediante carga de preguntas desde archivos JSON.
* Mantener un registro claro del progreso: preguntas respondidas, aciertos y avance actual.

---

# **Características principales**

## **1. Categorías disponibles**

El juego incluye categorías temáticas predefinidas:

* **Historia**
* **Ciencia**
* **Cultura Pop**
* **Películas**
* **Deportes**

Cada categoría se carga desde archivos JSON independientes en `assets/questions/`.

---

## **2. Modo de juego**

### **Nueva partida**

El jugador elige una o más categorías.

El sistema:

1. Carga todas las preguntas de los archivos JSON.
2. Filtra por las categorías seleccionadas.
3. Mezcla las preguntas aleatoriamente.
4. Genera una partida nueva con el listado ordenado aleatoriamente.

### **Continuar partida**

Si existe un progreso guardado, el jugador puede retomarlo exactamente en el punto donde lo dejó:

* Pregunta actual
* Aciertos
* Preguntas contestadas
* Orden original de preguntas

El juego guarda automáticamente después de cada respuesta.

---

## **3. Preguntas (Multiple Choice)**

Cada pregunta contiene:

* Enunciado
* Lista de 4 opciones
* Índice de la respuesta correcta
* Categoría
* ID único

El jugador elige una opción y recibe retroalimentación inmediata (acierto/error).

---

## **4. Sistema de puntuación**

* **Aciertos:** el jugador suma 1 punto por respuesta correcta.
* **Progreso:** el sistema registra cuántas preguntas ha contestado y cuántas quedan.
* **Resultados:** al final se muestra un resumen de desempeño.

---

## **5. Persistencia de datos**

El juego utiliza almacenamiento local para:

* Guardar y cargar partidas (`GameState`)
* Mantener puntaje, índice de pregunta actual y orden de preguntas
* Almacenamiento basado en SharedPreferences / archivo local (según plataforma)

---

# **Estructura técnica del proyecto**

## **Arquitectura simple, escalable y SOLID**

### **Clases principales**

* `Question` — Modelo de pregunta
* `Category` — Enum de categorías
* `GameState` — Representa el estado completo de la partida
* `GameStateStorage` — Interfaz para guardar/cargar
* `QuestionRepository` — Interfaz para cargar preguntas
* `JsonQuestionRepository` — Carga de preguntas desde archivos JSON
* `GameEngine` — Controlador principal del juego

---

# **Estructura de assets**

```
assets/
   questions/
      history.json
      science.json
      movies.json
      pop_culture.json
      sports.json
```

Cada archivo contiene únicamente preguntas de su categoría.

---

# **Formato de JSON**

```json
[
  {
    "id": "h1",
    "category": "HISTORY",
    "text": "¿En qué año ocurrió la caída del Imperio romano de Occidente?",
    "options": ["476 d.C.", "1492 d.C.", "800 d.C.", "1215 d.C."],
    "correctIndex": 0
  }
]
```

---

# **Flujo de juego**

1. Menú principal:

   * Nueva partida
   * Continuar partida

2. Selección de categorías

3. El motor selecciona y mezcla preguntas

4. El jugador responde una pregunta a la vez

5. Se registra acierto/error

6. Guardado automático

7. Cuando no quedan preguntas → pantalla de resultados

---

# **Interfaz y experiencia**

* UI minimalista y clara
* Feedback inmediato:

  * Acierto → verde
  * Error → rojo
* Animación ligera para mostrar transición entre preguntas
* Progreso visible (pregunta X de Y)

---

# **Resumen**

Who Knows it? es un juego de trivia **simple, rápido de implementar y fácil de expandir**, ideal para un proyecto académico en tiempo limitado, pero con base sólida para evolucionar a una app completa en el futuro.
