package com.example.s08_formularioasistencia.ui.form

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.s08_formularioasistencia.R
import com.example.s08_formularioasistencia.data.model.Attendee
import com.example.s08_formularioasistencia.data.model.AttendeeType
import com.example.s08_formularioasistencia.databinding.FragmentFormBinding
import java.text.SimpleDateFormat
import java.util.*

class FormFragment : Fragment() {
    private var _binding: FragmentFormBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FormViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAttendeeTypeSpinner()
        setupCurrentTime()
        setupForm()
    }

    private fun setupAttendeeTypeSpinner() {
        val types = listOf(getString(R.string.student), getString(R.string.teacher))
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, types)
        binding.spinnerAttendeeType.setAdapter(adapter)

        // Set default value
        binding.spinnerAttendeeType.setText(types[0], false)
        updateFormFields(AttendeeType.STUDENT)

        binding.spinnerAttendeeType.setOnItemClickListener { _, _, position, _ ->
            val selectedType = if (position == 0) AttendeeType.STUDENT else AttendeeType.TEACHER
            updateFormFields(selectedType)
        }
    }


    private fun isStudentSelected(): Boolean {
        return binding.spinnerAttendeeType.text.toString() == getString(R.string.student)
    }

    private fun updateFormFields(type: AttendeeType) {
        binding.studentCodeContainer.visibility =
            if (type == AttendeeType.STUDENT) View.VISIBLE else View.GONE
        binding.dniContainer.visibility =
            if (type == AttendeeType.TEACHER) View.VISIBLE else View.GONE
    }

    private fun setupCurrentTime() {
        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val currentTime = timeFormat.format(Date())
        binding.textCurrentTime.text = currentTime

        // Actualizar cada segundo usando Handler(Looper.getMainLooper()) para evitar deprecated
        val handler = android.os.Handler(android.os.Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                val updatedTime = timeFormat.format(Date())
                binding.textCurrentTime.text = updatedTime
                handler.postDelayed(this, 1000)
            }
        }
        handler.postDelayed(runnable, 1000)
    }

    private fun setupForm() {
        binding.buttonRegister.setOnClickListener {
            if (validateForm()) {
                registerAttendee()
            }
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true

        // Validar nombres
        if (binding.editFirstName.text.isNullOrEmpty()) {
            binding.textInputFirstName.error = getString(R.string.required_field)
            isValid = false
        } else {
            binding.textInputFirstName.error = null
        }

        // Validar apellidos
        if (binding.editLastName.text.isNullOrEmpty()) {
            binding.textInputLastName.error = getString(R.string.required_field)
            isValid = false
        } else {
            binding.textInputLastName.error = null
        }

        // Validar correo
        if (binding.editEmail.text.isNullOrEmpty()) {
            binding.textInputEmail.error = getString(R.string.required_field)
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.editEmail.text.toString()).matches()) {
            binding.textInputEmail.error = getString(R.string.invalid_email)
            isValid = false
        } else {
            binding.textInputEmail.error = null
        }

        // Validar celular
        if (binding.editPhone.text.isNullOrEmpty()) {
            binding.textInputPhone.error = getString(R.string.required_field)
            isValid = false
        } else {
            binding.textInputPhone.error = null
        }

        // Validar campos específicos
        val selected = binding.spinnerAttendeeType.text.toString()
        val isStudent = selected == getString(R.string.student)
        if (isStudent) {
            if (binding.editStudentCode.text.isNullOrEmpty()) {
                binding.textInputStudentCode.error = getString(R.string.required_field)
                isValid = false
            } else {
                binding.textInputStudentCode.error = null
            }
        } else {
            if (binding.editDni.text.isNullOrEmpty()) {
                binding.textInputDni.error = getString(R.string.required_field)
                isValid = false
            } else {
                binding.textInputDni.error = null
            }
        }

        return isValid
    }

    private fun registerAttendee() {
        val selected = binding.spinnerAttendeeType.text.toString()
        val isStudent = selected == getString(R.string.student)
        val type = if (isStudent) AttendeeType.STUDENT else AttendeeType.TEACHER

        val attendee = Attendee(
            id = UUID.randomUUID().toString(),
            type = type,
            firstName = binding.editFirstName.text.toString(),
            lastName = binding.editLastName.text.toString(),
            email = binding.editEmail.text.toString(),
            phone = binding.editPhone.text.toString(),
            studentCode = if (isStudent) binding.editStudentCode.text.toString() else null,
            dni = if (!isStudent) binding.editDni.text.toString() else null,
            comments = binding.editComments.text?.toString(),
            registrationDate = Date()
        )

        viewModel.saveAttendee(attendee)
        Toast.makeText(context, R.string.registration_success, Toast.LENGTH_SHORT).show()
        clearForm()
    }

    private fun clearForm() {
        binding.editFirstName.text?.clear()
        binding.editLastName.text?.clear()
        binding.editEmail.text?.clear()
        binding.editPhone.text?.clear()
        binding.editStudentCode.text?.clear()
        binding.editDni.text?.clear()
        binding.editComments.text?.clear()
    }

    @Deprecated("Usar el nuevo sistema de menú de fragmentos", ReplaceWith("requireActivity().addMenuProvider(...)"))
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Usar el nuevo sistema de menú de fragmentos", ReplaceWith("requireActivity().addMenuProvider(...)"))
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_event_info -> {
                showEventInfoDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showEventInfoDialog() {
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle(R.string.menu_event_info)
            .setMessage(getString(R.string.event_name) + "\n\n" +
                    getString(R.string.event_date) + "\n" +
                    getString(R.string.event_location) + "\n\n" +
                    getString(R.string.event_activities) + ":\n" +
                    getString(R.string.event_activities_list) + "\n\n" +
                    getString(R.string.event_contact) + ":\n" +
                    getString(R.string.event_contact_info))
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}