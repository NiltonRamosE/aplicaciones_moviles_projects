package com.example.s08_formularioasistencia.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.s08_formularioasistencia.data.repository.AttendanceRepository
import com.example.s08_formularioasistencia.databinding.ActivityAttendeesListBinding
import com.example.s08_formularioasistencia.ui.adapters.AttendeeAdapter

class AttendeesListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAttendeesListBinding
    private lateinit var repository: AttendanceRepository
    private lateinit var sharedPrefs: SharedPreferences

    companion object {
        private const val KEY_ATTENDEES = "attendees_list"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAttendeesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar SharedPreferences
        sharedPrefs = getSharedPreferences("attendance_prefs", Context.MODE_PRIVATE)
        repository = AttendanceRepository(applicationContext)

        setupRecyclerView()
        verifySavedData()
    }

    private fun setupRecyclerView() {
        val attendees = repository.getAllAttendees()
        Log.d("AttendeesList", "Total attendees: ${attendees.size}")
        attendees.forEach { Log.d("AttendeeData", it.toString()) }

        val adapter = AttendeeAdapter(attendees)
        binding.rvAttendees.adapter = adapter

        if (attendees.isEmpty()) {
            Toast.makeText(this, "No hay asistentes registrados", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Mostrando ${attendees.size} asistentes", Toast.LENGTH_SHORT).show()
        }
    }

    private fun verifySavedData() {
        // Verifica directamente el SharedPreferences
        val json = sharedPrefs.getString(KEY_ATTENDEES, "[]") ?: "[]"
        Log.d("SharedPrefsData", "JSON saved: $json")
    }
}