package com.example.s05_clinicaroblesapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EspecialidadAdapter(private val listaOriginal: List<Especialidad>) :
    RecyclerView.Adapter<EspecialidadAdapter.ViewHolder>() {
    // Lista que se usará para mostrar los datos (permite aplicar filtros sin modificar la original)
    private var listaFiltrada: MutableList<Especialidad> = listaOriginal.toMutableList()

    // ViewHolder que contiene las vistas de un ítem de especialidad
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgEspecialidad: ImageView = view.findViewById(R.id.imgEspecialidad)
        val txtNombre: TextView = view.findViewById(R.id.txtNombre)
        val txtDescripcion: TextView = view.findViewById(R.id.txtDescripcion)
    }

    // Infla el layout del ítem y crea una instancia del ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_especialidad, parent, false)
        return ViewHolder(view)
    }

    // Asocia los datos de la especialidad con las vistas del ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listaFiltrada[position]
        holder.imgEspecialidad.setImageResource(item.imagenResId)
        holder.txtNombre.text = item.nombre
        holder.txtDescripcion.text = item.descripcion

        // Acción al hacer clic en una especialidad: abrir la actividad de doctores
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DoctoresActivity::class.java)
            intent.putExtra("doctores", ArrayList(item.doctores))
            context.startActivity(intent)
        }

    }

    // Retorna el número de elementos en la lista filtrada
    override fun getItemCount(): Int = listaFiltrada.size

    fun filter(text: String) {
        listaFiltrada = if (text.isEmpty()) {
            listaOriginal.toMutableList() // Si está vacío, se muestra toda la lista
        } else {
            listaOriginal.filter {
                it.nombre.contains(text, ignoreCase = true) // Coincidencia con el nombre
            }.toMutableList()
        }
        notifyDataSetChanged() // Notifica al adaptador que los datos han cambiado
    }
}
