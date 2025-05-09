package com.example.s02_unsformregister

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(private var students: List<Student>) :
    RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val codigo: TextView = itemView.findViewById(R.id.item_codigo)
        val apellidos: TextView = itemView.findViewById(R.id.item_apellidos)
        val nombres: TextView = itemView.findViewById(R.id.item_nombres)
        val correo: TextView = itemView.findViewById(R.id.item_correo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.codigo.text = student.codigo
        holder.apellidos.text = student.apellidos
        holder.nombres.text = student.nombres
        holder.correo.text = student.correo
    }

    override fun getItemCount() = students.size

    fun updateData(newList: List<Student>) {
        students = newList
        notifyDataSetChanged()
    }
}
