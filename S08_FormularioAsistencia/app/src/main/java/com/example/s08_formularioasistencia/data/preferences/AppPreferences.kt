package com.example.s08_formularioasistencia.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.s08_formularioasistencia.data.model.Attendee
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AppPreferences(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("SanPedritoPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveAttendee(attendee: Attendee) {
        val attendees = getAttendeesList().toMutableList()
        attendees.add(attendee)
        val json = gson.toJson(attendees)
        sharedPreferences.edit().putString("attendees", json).apply()
    }


    fun getAttendees(): Flow<List<Attendee>> = flow {
        emit(getAttendeesList()) // Usa una función interna para evitar recursividad
    }

    private fun getAttendeesList(): List<Attendee> {
        val json = sharedPreferences.getString("attendees", null)
        return if (json != null) {
            val type = object : TypeToken<List<Attendee>>() {}.type
            gson.fromJson(json, type) ?: emptyList()
        } else {
            emptyList()
        }
    }

    fun clearAttendees() {
        sharedPreferences.edit().remove("attendees").apply()
    }
}