package com.example.s08_formularioasistencia.data.model

import java.util.Date

data class Attendee(
    val id: String,
    val type: AttendeeType,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val studentCode: String?,
    val dni: String?,
    val comments: String?,
    val registrationDate: Date
)