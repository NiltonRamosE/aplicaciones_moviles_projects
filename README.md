# 📱 Aplicaciones Móviles – Proyectos Semanales - SEGUNDA UNIDAD

Este repositorio contiene una serie de aplicaciones móviles desarrolladas durante la segunda unidad como parte de un reto de aprendizaje. Cada semana se desarrollará una aplicación distinta, enfocada en reforzar diferentes aspectos del desarrollo Android con Kotlin y Android Studio.

---

# 📱 Semana 08: Registro de Asistencia - Fiesta de San Pedrito 2025

Aplicación móvil para gestionar el registro de asistentes al evento cultural "Fiesta de San Pedrito 2025", desarrollada con Kotlin y Android Jetpack.

## ✨ Características Principales

### 📋 Sistema de Registro
- **Doble formulario** adaptado para:
  - 👨‍🎓 Estudiantes (con campo de matrícula)
  - 👩‍🏫 Docentes (con campo de DNI)
- ⏱ Registro automático de hora de asistencia
- 📝 Campo opcional para comentarios

### 👥 Gestión de Asistentes
- 🗂 Visualización en lista con tarjetas interactivas
- 🔍 Persistencia de datos local con SharedPreferences
- 🔄 Sincronización en tiempo real con LiveData

### 🎨 Experiencia de Usuario
- 🌗 Modo oscuro/claro configurable
- 🖥 WebView integrado para términos y condiciones
- 🧩 Interfaz con Material Design 3

## 🛠 Tecnologías Utilizadas

| Categoría         | Tecnologías                                                                 |
|-------------------|-----------------------------------------------------------------------------|
| Arquitectura      | MVVM (Model-View-ViewModel)                                                 |
| UI                | Jetpack Compose, ViewBinding, Fragments                                     |
| Datos             | LiveData, SharedPreferences, Gson                                           |
| Asincronía        | Corrutinas (Kotlin Coroutines)                                              |
| Estilos           | Material Components, Temas dinámicos (light/dark)                           |

## 🎨 Paleta de Colores

```xml
<!-- Colores principales -->
<color name="primary_color">#D20037</color>  <!-- Rojo institucional -->
<color name="primary_medium">#FF3369</color>
<color name="white">#FAFAFA</color>          <!-- Fondo claro -->
<color name="black">#121212</color>          <!-- Texto oscuro -->
```

**Capturas de pantalla:**  
<div align="center">
  <img src="images/s8_ui_1.png" width="200" style="margin-right:50px;" />
  <img src="images/s8_ui_2.png" width="200" style="margin-right:50px;" />
  <img src="images/s8_ui_3.png" width="200" style="margin-right:50px;"/>
  <img src="images/s8_ui_4.png" width="200" style="margin-right:50px;"/>
  <img src="images/s8_ui_5.png" width="200" style="margin-right:50px;"/>
  <img src="images/s8_ui_6.png" width="200" style="margin-right:50px;"/>
</div>
---

# 📱 Semana 09: AppS9 - Gestor de Preferencias Android

Aplicación Android moderna para gestión de preferencias con persistencia de datos usando SharedPreferences.

## ✨ Características

- **Contador de visitas** con botón de reinicio
- **Perfil de usuario** (nombre, edad, email)
- **Modo oscuro/claro** configurable
- **Diseño Material 3** moderno
- **Persistencia de datos** entre sesiones

**Capturas de pantalla:**  
<div align="center">
  <img src="images/s9_ui_1.png" width="200" style="margin-right:50px;" />
  <img src="images/s9_ui_2.png" width="200" style="margin-right:50px;" />
  <img src="images/s9_ui_3.png" width="200" style="margin-right:50px;"/>
</div>
---

🎯 **Objetivo:**  
Fortalecer el desarrollo de aplicaciones móviles Android, aplicando diferentes patrones, componentes UI/UX, y mejorando habilidades técnicas semana a semana.

📌 **Tecnologías Usadas:**
- Kotlin
- Android Studio
- RecyclerView
- CardView
- Intents
- ConstraintLayout
