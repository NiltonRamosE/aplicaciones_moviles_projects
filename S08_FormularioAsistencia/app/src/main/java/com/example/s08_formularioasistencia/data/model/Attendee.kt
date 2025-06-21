package com.example.s08_formularioasistencia.data.model

sealed class Attendee {
    abstract val firstName: String
    abstract val lastName: String
    abstract val email: String
    abstract val phone: String
    abstract val registrationTime: String
    abstract val comments: String?
}