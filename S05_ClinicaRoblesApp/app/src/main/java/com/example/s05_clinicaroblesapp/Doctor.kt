package com.example.s05_clinicaroblesapp

import java.io.Serializable

data class Doctor(
    val nombre: String,
    val horario: String
) : Serializable
