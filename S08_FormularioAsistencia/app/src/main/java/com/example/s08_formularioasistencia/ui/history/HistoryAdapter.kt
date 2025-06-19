package com.example.s08_formularioasistencia.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.s08_formularioasistencia.data.model.Attendee
import com.example.s08_formularioasistencia.data.model.AttendeeType
import com.example.s08_formularioasistencia.databinding.ItemAttendeeBinding
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter : ListAdapter<Attendee, HistoryAdapter.AttendeeViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendeeViewHolder {
        val binding = ItemAttendeeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AttendeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AttendeeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class AttendeeViewHolder(private val binding: ItemAttendeeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(attendee: Attendee) {
            binding.textName.text = "${attendee.firstName} ${attendee.lastName}"
            binding.textEmail.text = attendee.email
            binding.textType.text = if (attendee.type == AttendeeType.STUDENT) "Estudiante" else "Docente"

            val idText = if (attendee.type == AttendeeType.STUDENT) {
                "Código: ${attendee.studentCode ?: ""}"
            } else {
                "DNI: ${attendee.dni ?: ""}"
            }
            binding.textId.text = idText

            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            binding.textDate.text = dateFormat.format(attendee.registrationDate)
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Attendee>() {
        override fun areItemsTheSame(oldItem: Attendee, newItem: Attendee): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Attendee, newItem: Attendee): Boolean {
            return oldItem == newItem
        }
    }
}