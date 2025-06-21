package com.example.s08_formularioasistencia.data.repository

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.s08_formularioasistencia.viewmodel.AttendanceViewModel

class AttendanceViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AttendanceViewModel::class.java)) {
            return AttendanceViewModel(
                AttendanceRepository(application.applicationContext)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}