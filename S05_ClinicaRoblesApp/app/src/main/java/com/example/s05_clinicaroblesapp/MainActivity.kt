package com.example.s05_clinicaroblesapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.btnEspecialidades)
        btn.setOnClickListener {
            val intent = Intent(this, EspecialidadesActivity::class.java)
            startActivity(intent)
        }

    }
}
