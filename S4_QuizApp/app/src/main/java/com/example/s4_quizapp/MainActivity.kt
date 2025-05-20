package com.example.s4_quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var usernameEt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usernameEt = findViewById(R.id.usernameEt)
        val initBtn = findViewById<Button>(R.id.initBtn)

        initBtn.setOnClickListener {
            val username = usernameEt.text.toString().trim()
            if (username.isEmpty()) {
                Toast.makeText(this, "Por favor ingresa un nombre de usuario", Toast.LENGTH_SHORT).show()
            } else {
                // Pasar el nombre de usuario a QuestionActivity
                val intent = Intent(this, QuestionActivity::class.java)
                intent.putExtra("USERNAME", username)
                startActivity(intent)
            }
        }
    }
}