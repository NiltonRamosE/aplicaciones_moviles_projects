package com.example.s05_clinicaroblesapp

data class Especialidad(
    val nombre: String,
    val descripcion: String,
    val imagenResId: Int,
    val doctores: List<Doctor>
)
