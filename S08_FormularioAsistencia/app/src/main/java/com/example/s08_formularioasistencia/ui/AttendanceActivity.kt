package com.example.s08_formularioasistencia.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.s08_formularioasistencia.R
import com.example.s08_formularioasistencia.data.repository.AttendanceViewModelFactory
import com.example.s08_formularioasistencia.ui.fragments.StudentFormFragment
import com.example.s08_formularioasistencia.ui.fragments.TeacherFormFragment
import com.example.s08_formularioasistencia.viewmodel.AttendanceViewModel
import com.example.s08_formularioasistencia.viewmodel.AttendeeType
import com.example.s08_formularioasistencia.databinding.ActivityAttendanceBinding

class AttendanceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAttendanceBinding
    private lateinit var viewModel: AttendanceViewModel
    private lateinit var appCompatDelegate: AppCompatDelegate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAttendanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appCompatDelegate = delegate
        setSupportActionBar(binding.toolbar)
        setupViewModel()
        setupListeners()

        binding.btnViewAttendees.setOnClickListener {
            startActivity(Intent(this, AttendeesListActivity::class.java))
        }
    }

    private fun setupViewModel() {
        val factory = AttendanceViewModelFactory(application)
        viewModel = ViewModelProvider(this, factory).get(AttendanceViewModel::class.java)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.currentTime.observe(this) { time ->
            binding.tvCurrentTime.text = getString(R.string.current_time, time)
        }

        viewModel.attendeeType.observe(this) { type ->
            showFormForType(type)
        }

        viewModel.registrationSuccess.observe(this) { success ->
            if (success) {
                Toast.makeText(this, R.string.registration_success, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.errorMessage.observe(this) { error ->
            if (error.isNotBlank()) {
                Toast.makeText(this, error, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupListeners() {
        binding.btnStudent.setOnClickListener {
            viewModel.setAttendeeType(AttendeeType.STUDENT)
        }

        binding.btnTeacher.setOnClickListener {
            viewModel.setAttendeeType(AttendeeType.TEACHER)
        }
    }

    private fun showFormForType(type: AttendeeType) {
        val fragment = when (type) {
            AttendeeType.STUDENT -> StudentFormFragment()
            AttendeeType.TEACHER -> TeacherFormFragment()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.formContainer, fragment)
            .commit()
    }

    // Menú de opciones
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        // Configurar estado inicial del modo oscuro
        val darkModeItem = menu.findItem(R.id.action_dark_mode)
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> darkModeItem.setTitle(R.string.light_mode)
            Configuration.UI_MODE_NIGHT_NO -> darkModeItem.setTitle(R.string.dark_mode)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_dark_mode -> {
                toggleDarkMode(item)
                true
            }
            R.id.action_terms -> {
                showTermsAndConditions()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun toggleDarkMode(menuItem: MenuItem) {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val newNightMode = when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_YES -> {
                menuItem.title = getString(R.string.dark_mode)
                AppCompatDelegate.MODE_NIGHT_NO
            }
            else -> {
                menuItem.title = getString(R.string.light_mode)
                AppCompatDelegate.MODE_NIGHT_YES
            }
        }

        AppCompatDelegate.setDefaultNightMode(newNightMode)
        delegate.localNightMode = newNightMode
    }

    private fun showTermsAndConditions() {
        val intent = Intent(this, TermsActivity::class.java)
        startActivity(intent)
    }
}