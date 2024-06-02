package com.uv.routinesappuv.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.uv.routinesappuv.R
import com.uv.routinesappuv.databinding.FragmentAddRoutineBinding

class AddRoutineFragment : Fragment() {
    private lateinit var binding: FragmentAddRoutineBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddRoutineBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        setupToolbar()
        setupSpinners()

        return binding.root
    }

    private fun setupToolbar() {
        binding.contentToolbar.toolbarAddRoutine.setNavigationOnClickListener { onBackPressed() }
    }
    private fun onBackPressed() {
        findNavController().navigate(R.id.action_go_back_home)
    }

    private fun setupSpinners() {
        // Valores de prueba para los Spinners
        val exercises = arrayOf("Nombre ejercicio","Push Up", "Pull Up", "Squat", "Deadlift")
        val equipment = arrayOf("Equipamento", "Dumbbells", "Barbell", "Resistance Bands")

        // Configurar adaptador para el Spinner de ejercicios
        val exercisesAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, exercises)
        exercisesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.inputNombreEjercicio.adapter = exercisesAdapter

        // Configurar adaptador para el Spinner de equipamiento
        val equipmentAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, equipment)
        equipmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.inputEquipamiento.adapter = equipmentAdapter
    }
}
