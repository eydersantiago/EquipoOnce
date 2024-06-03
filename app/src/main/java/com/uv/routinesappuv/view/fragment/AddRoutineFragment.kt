package com.uv.routinesappuv.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

        // Initialize apiService before calling fetchExercises
        apiService = ApiUtils.getApiService()
        fetchExercises()

        setupToolbar()
        setupSpinners()
        setupListeners()

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
        val equipment = arrayOf(
            "Equipamento",
            "Cinta de correr (treadmill)",
            "Bicicleta estática",
            "Elíptica",
            "Máquina de remo",
            "Stepper o escaladora",
            "Cuerda para saltar",
            "Máquina de esquí de fondo (ski erg)",
            "Pesas libres (mancuernas y barras)",
            "Kettlebells",
            "Barras de dominadas",
            "Bandas de resistencia",
            "Máquinas de pesas (multi-gimnasio)",
            "Bancos de pesas ajustables",
            "Balones medicinales",
            "Sacos de arena (sandbags)",
            "Barras para pesas olímpicas y discos",
            "Cajas pliométricas",
            "TRX o sistemas de entrenamiento en suspensión",
            "Rueda abdominal",
            "Cuerdas de batalla (battle ropes)",
            "Balón suizo (fitball)",
            "Esterilla de yoga o colchoneta",
            "Rodillos de espuma (foam rollers)",
            "Barras de estiramiento",
            "Bloques y correas de yoga",
            "Cojines de equilibrio (bosu)",
            "Sin equipamento"
        )

        // Configurar adaptador para el Spinner de equipamiento
        val equipmentAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, equipment)
        equipmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.inputEquipamiento.adapter = equipmentAdapter
    }

    private fun setupListeners() {
        val textWatchers = listOf(
            binding.etDescripcion,
            binding.etSeries,
            binding.etRepeticiones,
            binding.etNombreRutina,
            binding.etDescripcion
        )

        for (textView in textWatchers) {
            textView.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    validateFields()
                }
                override fun afterTextChanged(s: Editable?) {}
            })
        }

        binding.btnAgregarEjercicio.setOnClickListener {
            if (binding.inputNombreEjercicio.selectedItem == "Nombre ejercicio" ||
                binding.inputEquipamiento.selectedItem == "Equipamento") {
                Toast.makeText(requireContext(), "Selecciona un nombre del ejercicio o equipamento", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "FUNCIONO", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnAgregarRutina.setOnClickListener {
            if (binding.etNombreRutina.text.toString().isEmpty() ||
                binding.etDescripcion.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "FUNCIONO RUTINA", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun validateFields() {
        val isDescripcionFilled = binding.etDescripcion.text.toString().isNotEmpty()
        val isSeriesFilled = binding.etSeries.text.toString().isNotEmpty()
        val isRepeticionesFilled = binding.etRepeticiones.text.toString().isNotEmpty()
        val isNombreRutinaFilled = binding.etNombreRutina.text.toString().isNotEmpty()
        val isDescripcionRutinaFilled = binding.etDescripcionRutina.text.toString().isNotEmpty()

        binding.btnAgregarEjercicio.isEnabled = isDescripcionFilled && isSeriesFilled && isRepeticionesFilled
        binding.btnAgregarRutina.isEnabled = isNombreRutinaFilled && isDescripcionRutinaFilled
    }

}
