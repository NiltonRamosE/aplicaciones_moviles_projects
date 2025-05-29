package com.example.s05_clinicaroblesapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DoctoresActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctores)

        val listaDoctores = intent.getSerializableExtra("doctores") as ArrayList<Doctor>

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerDoctores)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = DoctorAdapter(listaDoctores)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_doctores)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        val tvInfo = findViewById<TextView>(R.id.tvInfo)
        tvInfo.text = "Elige un doctor para ver su horario y especialidad. Actualmente tenemos: ${listaDoctores.size} disponibles"

    }
}
