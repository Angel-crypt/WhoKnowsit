# Who Knows it?

**Descripción:**  
Juego social y competitivo para 2 a 4 jugadores en un solo dispositivo, combinando preguntas y retos para fomentar conocimiento mutuo, diversión y competencia estratégica. Los jugadores acumulan puntos, ganan ítems limitados y participan en rondas dinámicas con interacción y votación.

**Objetivo general:**  
Fomentar la interacción social y competitividad mediante preguntas y retos, premiando el conocimiento entre jugadores y la toma de decisiones estratégicas.

**Objetivos específicos:**
- Ofrecer rondas de preguntas y retos con puntuación diferenciada.
- Permitir selección aleatoria de preguntas y retos según paquetes temáticos y número de rondas.
- Mantener turnos automáticos equitativos para todos los jugadores.
- Integrar ítems limitados de uso único para potenciar estrategia y competencia.
- Mostrar puntaje acumulado y estadísticas al final de la partida.
- Proporcionar retroalimentación visual y sonora que mejore la experiencia.
- Guardar récords y estadísticas para seguimiento de desempeño.

**Tipos de rondas:**
- **Pregunta Normal:** Trivia general (1–3 puntos).
- **Pregunta "Quién conoce mejor a quién":** Evalúa conocimiento sobre otros jugadores (1–3 puntos).
- **Reto con votación:** Desafíos simples con puntaje mayor (2–5 puntos) y posibilidad de ganar ítems.

**Selección de preguntas y retos:**
- Aleatoria según número de rondas y paquete temático elegido.
- Evita repeticiones para el mismo jugador; puede repetirse para distintos jugadores.

**Turnos:**
- Automáticos y secuenciales, asignando jugador activo y objetivo de cada pregunta o reto.
- Permiten responder preguntas, realizar retos y usar ítems estratégicos.

**Ítems:**
- **Skip Question:** Salta una pregunta sin perder puntos.
- **Double Points:** Duplica puntos de respuesta o reto.
- Obtenidos al completar retos exitosos mediante votación.

**Puntaje:**
- Acumulativo por respuestas y retos.
- Puntaje ajustado según dificultad; afectado por ítems.

**Condición de fin de juego:**
- Por número de preguntas/retos definidos al inicio o por puntaje objetivo.
- Al final se muestran: puntaje total, ranking de retos y quién conoce mejor a quién.

**Interfaz y animaciones:**
- Canvas + SurfaceView con animaciones simples (resaltar jugador, aparición de ítems, cambios de color).
- Sonidos asociados a aciertos, errores, cambio de turno y uso de ítems.

**Persistencia:**
- Guardado de puntajes máximos y estadísticas de partidas mediante SharedPreferences.
