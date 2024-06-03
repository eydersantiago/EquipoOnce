package com.uv.routinesappuv.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.uv.routinesappuv.R
import com.uv.routinesappuv.databinding.FragmentAddRoutineBinding
import com.uv.routinesappuv.webService.ApiService
import com.uv.routinesappuv.webService.ApiUtils
import com.uv.routinesappuv.webService.RoutinesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class AddRoutineFragment : Fragment() {
    private lateinit var binding: FragmentAddRoutineBinding
    private lateinit var apiService: ApiService
    private val routinesList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddRoutineBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        setupToolbar()
        // Initialize apiService before calling fetchExercises
        apiService = ApiUtils.getApiService()
        fetchExercises()

        return binding.root
    }

    private fun fetchExercises() {
        apiService.getExercises().enqueue(object : Callback<List<RoutinesResponse>> {
            override fun onResponse(
                call: Call<List<RoutinesResponse>>,
                response: Response<List<RoutinesResponse>>
            ) {
                if (response.isSuccessful) {
                    val routinesList = response.body()
                    if (routinesList != null) {
                        // Clear the list and add the default option
                        this@AddRoutineFragment.routinesList.clear()
                        this@AddRoutineFragment.routinesList.add("Nombre ejercicio")

                        // Add exercise names from the response
                        for (routine in routinesList) {
                            Log.d("Routine", "Name: ${routine.name}, Body Part: ${routine.bodyPart}")
                            this@AddRoutineFragment.routinesList.add(routine.name)
                        }

                        // Update the spinner with the new data
                        setupSpinners()
                    } else {
                        Log.e("fetchExercises", "Response body is null")
                    }
                } else {
                    Log.e("fetchExercises", "Failed to fetch exercises. Response code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<RoutinesResponse>>, t: Throwable) {
                Log.e("fetchExercises", "Error fetching exercises", t)
                Toast.makeText(requireContext(), "Error fetching exercises", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupToolbar() {
        binding.contentToolbar.toolbarAddRoutine.setNavigationOnClickListener { onBackPressed() }
    }

    private fun onBackPressed() {
        findNavController().navigate(R.id.action_go_back_home)
    }

    private fun setupSpinners() {
        // Configurar adaptador para el Spinner de ejercicios
        val exercisesAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, routinesList)
        exercisesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.inputNombreEjercicio.adapter = exercisesAdapter

        // Valores de prueba para el Spinner de equipamiento
        val equipment = arrayOf("Equipamento", "Dumbbells", "Barbell", "Resistance Bands")

        // Configurar adaptador para el Spinner de equipamiento
        val equipmentAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, equipment)
        equipmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.inputEquipamiento.adapter = equipmentAdapter
    }
}
