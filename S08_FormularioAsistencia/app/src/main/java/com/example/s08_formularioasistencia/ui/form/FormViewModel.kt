package com.example.s08_formularioasistencia.ui.form

import androidx.lifecycle.ViewModel
import com.example.s08_formularioasistencia.data.model.Attendee
import com.example.s08_formularioasistencia.data.preferences.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FormViewModel @Inject constructor(
    private val preferences: AppPreferences
) : ViewModel() {

    fun saveAttendee(attendee: Attendee) {
        preferences.saveAttendee(attendee)
    }
}