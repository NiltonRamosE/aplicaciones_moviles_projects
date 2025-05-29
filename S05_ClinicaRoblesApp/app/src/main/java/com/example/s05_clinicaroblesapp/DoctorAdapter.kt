package com.example.s05_clinicaroblesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DoctorAdapter(private val lista: List<Doctor>) :
    RecyclerView.Adapter<DoctorAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombre: TextView = view.findViewById(R.id.txtNombreDoctor)
        val txtHorario: TextView = view.findViewById(R.id.txtHorarioDoctor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_doctor, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val doctor = lista[position]
        holder.txtNombre.text = doctor.nombre
        holder.txtHorario.text = doctor.horario
    }
}
