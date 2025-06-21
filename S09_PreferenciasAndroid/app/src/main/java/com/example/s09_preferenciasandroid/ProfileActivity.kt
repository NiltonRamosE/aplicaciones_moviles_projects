package com.example.s09_preferenciasandroid

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private lateinit var editTextName: EditText
    private lateinit var editTextAge: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var buttonSaveProfile: Button
    private lateinit var buttonLoadProfile: Button
    private lateinit var textViewProfileInfo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        sharedPreferencesHelper = SharedPreferencesHelper(this)

        initViews()
        setupListeners()
    }

    private fun initViews() {
        editTextName = findViewById(R.id.editTextName)
        editTextAge = findViewById(R.id.editTextAge)
        editTextEmail = findViewById(R.id.editTextEmail)
        buttonSaveProfile = findViewById(R.id.buttonSaveProfile)
        buttonLoadProfile = findViewById(R.id.buttonLoadProfile)
        textViewProfileInfo = findViewById(R.id.textViewProfileInfo)
    }

    private fun setupListeners() {
        buttonSaveProfile.setOnClickListener {
            saveProfile()
        }

        buttonLoadProfile.setOnClickListener {
            loadProfile()
        }
    }

    private fun saveProfile() {
        val name = editTextName.text.toString().trim()
        val age = editTextAge.text.toString().trim()
        val email = editTextEmail.text.toString().trim()

        if (name.isEmpty() || age.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        sharedPreferencesHelper.saveString(SharedPreferencesHelper.KEY_PROFILE_NAME, name)
        sharedPreferencesHelper.saveInt(SharedPreferencesHelper.KEY_PROFILE_AGE, age.toInt())
        sharedPreferencesHelper.saveString(SharedPreferencesHelper.KEY_PROFILE_EMAIL, email)

        Toast.makeText(this, "Perfil guardado exitosamente", Toast.LENGTH_SHORT).show()
    }

    private fun loadProfile() {
        val name = sharedPreferencesHelper.getString(SharedPreferencesHelper.KEY_PROFILE_NAME, "No definido")
        val age = sharedPreferencesHelper.getInt(SharedPreferencesHelper.KEY_PROFILE_AGE, 0)
        val email = sharedPreferencesHelper.getString(SharedPreferencesHelper.KEY_PROFILE_EMAIL, "No definido")

        val profileInfo = "Nombre: $name\nEdad: $age\nEmail: $email"
        textViewProfileInfo.text = profileInfo
    }
}