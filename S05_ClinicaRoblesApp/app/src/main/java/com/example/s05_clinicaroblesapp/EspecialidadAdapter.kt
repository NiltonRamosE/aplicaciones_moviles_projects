package com.example.s05_clinicaroblesapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EspecialidadAdapter(private val lista: List<Especialidad>) :
    RecyclerView.Adapter<EspecialidadAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgEspecialidad: ImageView = view.findViewById(R.id.imgEspecialidad)
        val txtNombre: TextView = view.findViewById(R.id.txtNombre)
        val txtDescripcion: TextView = view.findViewById(R.id.txtDescripcion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_especialidad, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]
        holder.imgEspecialidad.setImageResource(item.imagenResId)
        holder.txtNombre.text = item.nombre
        holder.txtDescripcion.text = item.descripcion

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DoctoresActivity::class.java)
            intent.putExtra("doctores", ArrayList(item.doctores))
            context.startActivity(intent)
        }

    }
}
