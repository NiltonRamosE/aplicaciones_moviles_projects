package com.example.s08_formularioasistencia.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.s08_formularioasistencia.data.model.Attendee
import com.example.s08_formularioasistencia.data.preferences.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val preferences: AppPreferences
) : ViewModel() {

    val attendees: LiveData<List<Attendee>> = preferences.getAttendees().asLiveData()
}
