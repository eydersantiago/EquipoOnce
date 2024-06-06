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
import com.uv.routinesappuv.model.Ejercicio
import com.uv.routinesappuv.model.Rutina
import com.uv.routinesappuv.webService.ApiService
import com.uv.routinesappuv.webService.ApiUtils
import com.uv.routinesappuv.webService.RoutinesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.uv.routinesappuv.repository.RutinasRepository
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch


class AddRoutineFragment : Fragment() {
    private lateinit var binding: FragmentAddRoutineBinding
    private lateinit var rutinasRepository: RutinasRepository
    private lateinit var apiService: ApiService
    private val routinesList = mutableListOf<String>()
    private val exerciseList = mutableListOf<Ejercicio>()
    private var countEjercicios = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddRoutineBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        // Initialize apiService before calling fetchExercises
        apiService = ApiUtils.getApiService()
        rutinasRepository = RutinasRepository(requireContext())
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
                saveExercise(binding)
            }
        }

        binding.btnAgregarRutina.setOnClickListener {
            if (binding.etNombreRutina.text.toString().isEmpty() ||
                binding.etDescripcionRutina.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                saveRoutineWithExercises(binding)
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

    private fun saveExercise(binding: FragmentAddRoutineBinding) {
        val nombreEjercicio = binding.inputNombreEjercicio.selectedItem.toString()
        val descripcion = binding.etDescripcion.text.toString()
        val equipamiento = binding.inputEquipamiento.selectedItem.toString()
        val series = binding.etSeries.text.toString().toIntOrNull() ?: 0 // Convertir a Int o usar 0 si es nulo
        val repeticiones = binding.etRepeticiones.text.toString().toIntOrNull() ?: 0 // Convertir a Int o usar 0 si es nulo

        if (nombreEjercicio.isNotEmpty() && descripcion.isNotEmpty() && equipamiento.isNotEmpty()) {
            val ejercicio = Ejercicio(
                id = countEjercicios++,
                nombre_ejercicio = nombreEjercicio,
                descripcion_ejercicio = descripcion,
                equipamento = equipamiento,
                series = series,
                repeticiones = repeticiones
            )

            // Agrega el ejercicio al array o haz con él lo que necesites
            exerciseList.add(ejercicio)

            // Limpia los campos después de agregar el ejercicio si es necesario
            clearFields(binding)

            Toast.makeText(context, "Ejercicio creado !!", Toast.LENGTH_SHORT).show()
            Log.e("EjerciciosCreados", "EJERCICIOS ${exerciseList} ")

        } else {
            Toast.makeText(context, "Por favor, completa todos los campos de ejercicio", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveRoutineWithExercises(binding: FragmentAddRoutineBinding) {
        val nombreRutina = binding.etNombreRutina.text.toString()
        val descripcionRutina = binding.etDescripcionRutina.text.toString()

        // Verificar si se ha ingresado el nombre y la descripción de la rutina
        if (nombreRutina.isNotEmpty() && descripcionRutina.isNotEmpty()) {
            // Crear la rutina
            val routine = Rutina (
                nombre_rutina = nombreRutina,
                descripcion_rutina = descripcionRutina,
                ejercicios = exerciseList
            )


            // Guardar la rutina en Firestore usando el repositorio
            lifecycleScope.launch {
                rutinasRepository.saveRoutine(routine)
                Toast.makeText(context, "Rutina creada con éxito", Toast.LENGTH_SHORT).show()
                countEjercicios = 0
                exerciseList.clear() // Limpiar la lista de ejercicios
                clearFields(binding)
            }

            onBackPressed()

        } else {
            Toast.makeText(context, "Por favor, completa todos los campos de la rutina", Toast.LENGTH_SHORT).show()
        }
    }


    private fun clearFields(binding: FragmentAddRoutineBinding) {
        binding.inputNombreEjercicio.setSelection(0)
        binding.etDescripcion.text.clear()
        binding.inputEquipamiento.setSelection(0) // Reinicia el Spinner a su posición inicial
        binding.etSeries.text.clear()
        binding.etRepeticiones.text.clear()
    }


}
