package com.example.s02_unsformregister

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        val btnHome = findViewById<ImageButton>(R.id.back_main)
        btnHome.setOnClickListener{
            finish()
        }

        val etCodigo = findViewById<EditText>(R.id.etCodigo)
        val etNombres = findViewById<EditText>(R.id.etNombres)
        val etApellidos = findViewById<EditText>(R.id.etApellidos)
        val etCorreo = findViewById<EditText>(R.id.etCorreo)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)

        btnRegistrar.setOnClickListener {
            val codigo = etCodigo.text.toString().trim()
            val nombres = etNombres.text.toString().trim()
            val apellidos = etApellidos.text.toString().trim()
            val correo = etCorreo.text.toString().trim()

            if (codigo.isEmpty() || nombres.isEmpty() || apellidos.isEmpty() || correo.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val estudiante = Student(codigo, apellidos, nombres, correo)
            StudentStorage.students.add(estudiante)

            Toast.makeText(this, "Estudiante registrado con éxito", Toast.LENGTH_SHORT).show()

            etCodigo.text.clear()
            etNombres.text.clear()
            etApellidos.text.clear()
            etCorreo.text.clear()
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down)
    }
}