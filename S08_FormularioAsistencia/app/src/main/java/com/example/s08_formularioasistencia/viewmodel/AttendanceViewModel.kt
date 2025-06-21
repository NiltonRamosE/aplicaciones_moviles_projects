package com.example.s08_formularioasistencia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.s08_formularioasistencia.data.model.Student
import com.example.s08_formularioasistencia.data.model.Teacher
import com.example.s08_formularioasistencia.data.repository.AttendanceRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Timer
import java.util.TimerTask

class AttendanceViewModel(private val repository: AttendanceRepository) : ViewModel() {
    private val _currentTime = MutableLiveData<String>()
    val currentTime: LiveData<String> = _currentTime

    private val _attendeeType = MutableLiveData<AttendeeType>()
    val attendeeType: LiveData<AttendeeType> = _attendeeType

    private val _registrationSuccess = MutableLiveData<Boolean>()
    val registrationSuccess: LiveData<Boolean> = _registrationSuccess

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    init {
        startClock()
    }

    private fun startClock() {
        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                _currentTime.postValue(timeFormat.format(Date()))
            }
        }, 0, 1000)
    }

    fun setAttendeeType(type: AttendeeType) {
        _attendeeType.value = type
    }

    fun registerStudent(student: Student) {
        viewModelScope.launch {
            try {
                if (student.firstName.isBlank() || student.lastName.isBlank()) {
                    throw IllegalArgumentException("Nombre y apellido son requeridos")
                }
                repository.registerStudent(student)
                _registrationSuccess.postValue(true)
            } catch (e: Exception) {
                _registrationSuccess.postValue(false)
                _errorMessage.postValue(e.message ?: "Error al registrar estudiante")
            }
        }
    }

    fun registerTeacher(teacher: Teacher) {
        viewModelScope.launch { // <-- Ahora debería funcionar
            repository.registerTeacher(teacher)
            _registrationSuccess.postValue(true)
        }
    }
}

enum class AttendeeType {
    STUDENT, TEACHER
}