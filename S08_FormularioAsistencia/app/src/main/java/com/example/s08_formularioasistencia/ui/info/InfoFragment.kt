package com.example.s08_formularioasistencia.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.s08_formularioasistencia.R
import com.example.s08_formularioasistencia.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textEventName.text = getString(R.string.event_name)
        binding.textEventDate.text = getString(R.string.event_date)
        binding.textEventLocation.text = getString(R.string.event_location)
        binding.textActivitiesTitle.text = getString(R.string.event_activities)
        binding.textActivities.text = getString(R.string.event_activities_list)
        binding.textContactTitle.text = getString(R.string.event_contact)
        binding.textContact.text = getString(R.string.event_contact_info)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}