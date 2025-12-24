🎓 Seguí Tu Carrera
Trackeá tu progreso universitario. Visual, simple y motivador.
<p align="center"> <img src="https://raw.githubusercontent.com/USUARIO/REPO/main/screenshots/banner.png" width="850"/> </p> <p align="center"> <img src="https://img.shields.io/badge/Kotlin-100%25-blueviolet"/> <img src="https://img.shields.io/badge/Jetpack%20Compose-UI-success"/> <img src="https://img.shields.io/badge/Room-SQLite-blue"/> <img src="https://img.shields.io/badge/Firebase-Auth-orange"/> <img src="https://img.shields.io/badge/Status-In%20Development-yellow"/> </p>
🚀 ¿Qué es Seguí Tu Carrera?

Seguí Tu Carrera es una app Android desarrollada con Kotlin y Jetpack Compose que ayuda a estudiantes universitarios a:

🎯 visualizar su progreso académico
📚 organizar materias por año
📊 llevar un control real y persistente de su carrera
💪 mantenerse motivados durante el recorrido universitario

Es un proyecto real, pensado desde la experiencia del estudiante y construido con arquitectura profesional.

✨ Features destacadas

✅ Autenticación segura con Firebase Auth
📦 Persistencia local con Room (SQLite)
🧠 Arquitectura MVVM + Repository
📊 UI reactiva con Flow + Compose
🗂️ Carga inicial de materias desde JSON
🔐 Datos asociados a cada usuario
🎨 Diseño moderno inspirado en Figma

📱 Screenshots
<p align="center"> <img src="https://raw.githubusercontent.com/USUARIO/REPO/main/screenshots/login.png" width="220"/> <img src="https://raw.githubusercontent.com/USUARIO/REPO/main/screenshots/home.png" width="220"/> <img src="https://raw.githubusercontent.com/USUARIO/REPO/main/screenshots/subjects.png" width="220"/> </p>

💡 La interfaz evoluciona junto con el proyecto.

🧱 Arquitectura & buenas prácticas
ui/            → Pantallas (Jetpack Compose)
viewmodel/     → Lógica de presentación
data/
 ├─ local/     → Room (Entities, DAO, Database)
 ├─ mapper/    → DTO → Entity
 ├─ repository → Single Source of Truth
auth/          → Firebase Authentication


✔ Separation of Concerns
✔ Reactive UI
✔ Escalable y mantenible
✔ Código orientado a producción

🛠️ Stack tecnológico

Kotlin

Jetpack Compose

Room (SQLite)

Firebase Authentication

Coroutines + Flow

Material 3

Gson

MVVM

📂 Data preload (JSON → Room)

Las materias se cargan automáticamente desde un archivo JSON:

[
  { "name": "Análisis Matemático I", "year": 1 },
  { "name": "Álgebra I", "year": 1 },
  { "name": "Física I", "year": 1 }
]


Esto permite:

inicialización limpia

persistencia local

experiencia offline-ready

🧪 Estado del proyecto

🚧 En desarrollo activo

Próximos hitos:

✔ Marcar materias como aprobadas

📊 Barra de progreso general

🏆 Logros y gamificación

⏱️ Pomodoro para estudio

📂 Tabs por año/cuatrimestre

🔒 Correlatividades

👨‍💻 Autor

Santiago Gómez
📍 Argentina
🎓 Estudiante universitario de programación
📱 Enfocado en desarrollo Android con Kotlin
💡 Interesado en UX, arquitectura y productos reales

⭐ ¿Te gustó el proyecto?

Si te resulta útil o interesante:

⭐ Dejá una estrella
🍴 Forkealo
🧠 Usalo como referencia
📢 Compartilo

🔥 Seguí tu carrera, paso a paso.
