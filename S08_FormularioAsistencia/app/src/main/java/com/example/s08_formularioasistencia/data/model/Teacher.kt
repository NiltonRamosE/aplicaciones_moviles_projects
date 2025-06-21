package com.example.s08_formularioasistencia.data.model

data class Teacher(
    override val firstName: String,
    override val lastName: String,
    override val email: String,
    override val phone: String,
    val dni: String,
    override val registrationTime: String,
    override val comments: String? = null
) : Attendee()