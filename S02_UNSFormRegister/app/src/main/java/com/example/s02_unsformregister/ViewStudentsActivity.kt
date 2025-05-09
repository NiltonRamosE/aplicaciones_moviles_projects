package com.example.s02_unsformregister

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ViewStudentsActivity : AppCompatActivity() {
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var students: MutableList<Student>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_students)

        val recyclerView = findViewById<RecyclerView>(R.id.students_recycler)
        val sortSpinner = findViewById<Spinner>(R.id.sort_spinner)
        val btnBackHome = findViewById<Button>(R.id.back_button)

        btnBackHome.setOnClickListener{
            val intent = Intent(this@ViewStudentsActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        students = StudentStorage.students


        studentAdapter = StudentAdapter(students)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = studentAdapter

        sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val sortedList = when (position) {
                    0 -> students.sortedBy { it.codigo }
                    1 -> students.sortedBy { it.apellidos }
                    else -> students
                }
                studentAdapter.updateData(sortedList)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No hacer nada
            }
        }
    }
}
