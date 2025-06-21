package com.example.s08_formularioasistencia.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.s08_formularioasistencia.R
import com.example.s08_formularioasistencia.data.model.Attendee
import com.example.s08_formularioasistencia.data.model.Student
import com.example.s08_formularioasistencia.data.model.Teacher

class AttendeeAdapter(private val attendees: List<Attendee>) :
    RecyclerView.Adapter<AttendeeAdapter.AttendeeViewHolder>() {

    inner class AttendeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvType: TextView = itemView.findViewById(R.id.tvType)
        private val tvEmail: TextView = itemView.findViewById(R.id.tvEmail)
        private val tvPhone: TextView = itemView.findViewById(R.id.tvPhone)
        private val tvIdNumber: TextView = itemView.findViewById(R.id.tvIdNumber)
        private val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        private val tvComments: TextView = itemView.findViewById(R.id.tvComments)

        fun bind(attendee: Attendee) {
            // Datos comunes a todos los asistentes
            tvName.text = "${attendee.firstName} ${attendee.lastName}"
            tvEmail.text = attendee.email
            tvPhone.text = "Teléfono: ${attendee.phone}"
            tvTime.text = "Registrado: ${attendee.registrationTime}"

            // Manejo específico para cada tipo
            when (attendee) {
                is Student -> {
                    tvType.text = "Estudiante"
                    tvIdNumber.text = "Matrícula: ${attendee.enrollmentCode}"
                }
                is Teacher -> {
                    tvType.text = "Docente"
                    tvIdNumber.text = "DNI: ${attendee.dni}"
                }
            }

            // Manejo de comentarios (opcional)
            attendee.comments?.takeIf { it.isNotBlank() }?.let {
                tvComments.text = "Comentarios: $it"
                tvComments.visibility = View.VISIBLE
            } ?: run {
                tvComments.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendeeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_attendee, parent, false)
        return AttendeeViewHolder(view)
    }

    override fun onBindViewHolder(holder: AttendeeViewHolder, position: Int) {
        holder.bind(attendees[position])
    }

    override fun getItemCount(): Int = attendees.size

    companion object {
        // Función útil para formatear la hora si es necesario
        fun formatTime(timeString: String): String {
            return try {
                // Puedes añadir formato de fecha aquí si lo necesitas
                timeString
            } catch (e: Exception) {
                timeString
            }
        }
    }
}