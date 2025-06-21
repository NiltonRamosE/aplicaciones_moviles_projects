package com.example.s08_formularioasistencia.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.s08_formularioasistencia.data.model.Teacher
import com.example.s08_formularioasistencia.viewmodel.AttendanceViewModel
import com.example.s08_formularioasistencia.databinding.FragmentTeacherFormBinding

class TeacherFormFragment : Fragment() {
    private lateinit var binding: FragmentTeacherFormBinding
    private lateinit var viewModel: AttendanceViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTeacherFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(AttendanceViewModel::class.java)

        binding.btnSubmit.setOnClickListener {
            registerTeacher()
        }
    }

    private fun registerTeacher() {
        val currentTime = viewModel.currentTime.value ?: ""

        val teacher = Teacher(
            firstName = binding.etFirstName.text.toString(),
            lastName = binding.etLastName.text.toString(),
            email = binding.etEmail.text.toString(),
            phone = binding.etPhone.text.toString(),
            dni = binding.etDni.text.toString(),
            registrationTime = currentTime,
            comments = binding.etComments.text.toString().takeIf { it.isNotBlank() }
        )

        viewModel.registerTeacher(teacher)
    }
}