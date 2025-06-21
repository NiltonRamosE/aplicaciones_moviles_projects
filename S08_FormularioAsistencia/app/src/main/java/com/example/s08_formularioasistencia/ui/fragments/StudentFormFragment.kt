package com.example.s08_formularioasistencia.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.s08_formularioasistencia.data.model.Student
import com.example.s08_formularioasistencia.viewmodel.AttendanceViewModel
import com.example.s08_formularioasistencia.databinding.FragmentStudentFormBinding

class StudentFormFragment : Fragment() {
    private lateinit var binding: FragmentStudentFormBinding
    private lateinit var viewModel: AttendanceViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentStudentFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(AttendanceViewModel::class.java)

        binding.btnSubmit.setOnClickListener {
            registerStudent()
        }
    }

    private fun registerStudent() {
        val currentTime = viewModel.currentTime.value ?: ""

        val student = Student(
            firstName = binding.etFirstName.text.toString(),
            lastName = binding.etLastName.text.toString(),
            email = binding.etEmail.text.toString(),
            phone = binding.etPhone.text.toString(),
            enrollmentCode = binding.etEnrollmentCode.text.toString(),
            registrationTime = currentTime,
            comments = binding.etComments.text.toString().takeIf { it.isNotBlank() }
        )

        viewModel.registerStudent(student)
    }
}