# 📱 Aplicaciones Móviles – Proyectos Semanales - TERCERA UNIDAD

Este repositorio contiene una serie de aplicaciones móviles desarrolladas durante la tercera unidad como parte de un reto de aprendizaje. Cada semana se desarrollará una aplicación distinta, enfocada en reforzar diferentes aspectos del desarrollo Android con Kotlin y Android Studio.

---

## 📅 Semana 12: Calculadora Básica

### 📋 Descripción
Aplicación de calculadora básica para Android, que permite realizar operaciones aritméticas y algunas funciones avanzadas. Fue desarrollada siguiendo la arquitectura **MVVM** e integrando componentes modernos de Android.

---

### 🖼️ Capturas de pantalla

<div align="center">
  <img src="images/s12_ui_1.png" width="200" style="margin-right:50px;"/>
  <img src="images/s12_ui_2.png" width="200" style="margin-right:50px;"/>
  <img src="images/s12_ui_3.png" width="200" style="margin-right:50px;"/>
</div>

---

## 🏗️ Arquitectura MVVM

La aplicación sigue el patrón de diseño **Model-View-ViewModel (MVVM)**, lo cual permite separar la lógica de negocio, presentación e interfaz de usuario de manera clara y escalable.


```kotlin
// Importaciones clave
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
```
### 📦 Estructura de componentes

#### 📁 Model (`CalculatorModel.kt`)
- Contiene la lógica pura de negocios y cálculos matemáticos.
- No tiene dependencias de Android ni conoce la UI.

#### 📁 ViewModel (`CalculatorViewModel.kt`)
- Utiliza `MutableLiveData` para representar estados mutables.
- Expone `LiveData` para que la View observe.
- Intermedia entre la lógica del modelo y la vista.

```kotlin
private val _currentInput = MutableLiveData<String>("0")
val currentInput: LiveData<String> = _currentInput
```
#### 📁 View (MainActivity.kt)
- Observa los cambios del ViewModel mediante LiveData.
- Se encarga de manejar la interacción del usuario.
- No contiene lógica de negocio.

### 🔄 Diagrama de flujo de datos (LiveData)

```mermaid
sequenceDiagram
    participant View
    participant ViewModel
    participant Model

    View->>ViewModel: Evento (click botón)
    ViewModel->>Model: Solicita cálculo
    Model-->>ViewModel: Retorna resultado
    ViewModel->>ViewModel: Actualiza MutableLiveData
    ViewModel-->>View: Notifica a través de LiveData
    View->>View: Actualiza UI
```

## 📅 Semana 13: TechPoint - Mapa de Tiendas de Cómputo

### 📋 Descripción
Aplicación que muestra en un mapa interactivo los establecimientos de venta de equipos de cómputo en Chimbote, con funcionalidades de:
- Geolocalización del usuario
- Recomendación de tiendas cercanas
- Información detallada de cada establecimiento

### 🖼️ Capturas de pantalla

<div align="center">
  <img src="images/s13_ui_1.jpeg" width="200" style="margin-right:50px;"/>
  <img src="images/s13_ui_2.jpeg" width="200" style="margin-right:50px;"/>
  <img src="images/s13_ui_3.jpeg" width="200" style="margin-right:50px;"/>
  <img src="images/s13_ui_4.jpeg" width="200" style="margin-right:50px;"/>
  <img src="images/s13_ui_5.jpeg" width="200" style="margin-right:50px;"/>
</div>

### 🛠️ Tecnologías Utilizadas
- Google Maps SDK para Android
- Fused Location Provider API
- Sistema de permisos en Android
- Material Design Components

### 📍 Funcionalidades Clave

```kotlin
// Detección de ubicación
private fun getCurrentLocation() {
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        location?.let {
            currentLocation = LatLng(it.latitude, it.longitude)
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation!!, 15f))
            showNearestStore()
        }
    }
}
```
### 🗺️ Estructura de Datos Principal
```kotlin
data class ComputerStore(
    val name: String, 
    val location: LatLng, 
    val address: String
)
```
### 🎯 Características Destacadas
- Mapa centrado en Chimbote con zoom inicial
- 10 tiendas de cómputo geolocalizadas
- Spinner para selección manual de tiendas
- Botón para encontrar la tienda más cercana
- Cálculo de distancia en kilómetros