package com.example.s08_formularioasistencia.data.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.s08_formularioasistencia.data.model.Attendee
import com.example.s08_formularioasistencia.data.model.Student
import com.example.s08_formularioasistencia.data.model.Teacher
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.reflect.Type

class AttendanceRepository(context: Context) {
    private val sharedPrefs = context.getSharedPreferences("attendance_prefs", Context.MODE_PRIVATE)

    private val gson = GsonBuilder()
        .registerTypeAdapter(Attendee::class.java, object : JsonSerializer<Attendee>, JsonDeserializer<Attendee> {
            override fun serialize(src: Attendee, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
                return when (src) {
                    is Student -> context.serialize(src, Student::class.java)
                    is Teacher -> context.serialize(src, Teacher::class.java)
                }
            }

            override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Attendee {
                val jsonObject = json.asJsonObject
                return if (jsonObject.has("enrollmentCode")) {
                    context.deserialize(json, Student::class.java)
                } else {
                    context.deserialize(json, Teacher::class.java)
                }
            }
        })
        .create()

    companion object {
        private const val KEY_ATTENDEES = "attendees_list"
    }

    suspend fun registerStudent(student: Student) {
        withContext(Dispatchers.IO) {
            val currentList = getAllAttendees().toMutableList()
            currentList.add(student)
            saveAttendees(currentList)
        }
    }

    suspend fun registerTeacher(teacher: Teacher) {
        withContext(Dispatchers.IO) {
            val currentList = getAllAttendees().toMutableList()
            currentList.add(teacher)
            saveAttendees(currentList)
        }
    }

    fun getAllAttendees(): List<Attendee> {
        val json = sharedPrefs.getString(KEY_ATTENDEES, "[]") ?: "[]"
        return try {
            val type = object : TypeToken<List<Attendee>>() {}.type
            gson.fromJson(json, type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun saveAttendees(attendees: List<Attendee>) {
        val savedData = sharedPrefs.getString(KEY_ATTENDEES, "[]")
        Log.d("RegistrationDebug", "Datos guardados: $savedData")
        val json = gson.toJson(attendees)
        sharedPrefs.edit().putString(KEY_ATTENDEES, json).apply()
    }
}