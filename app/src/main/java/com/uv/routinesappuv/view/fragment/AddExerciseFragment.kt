package com.uv.routinesappuv.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.uv.routinesappuv.R
import com.uv.routinesappuv.databinding.FragmentAddRoutineBinding
import com.uv.routinesappuv.databinding.FragmentEditRoutineBinding
import com.uv.routinesappuv.model.Ejercicio
import com.uv.routinesappuv.model.Rutina
import com.uv.routinesappuv.webService.ApiService
import com.uv.routinesappuv.webService.ApiUtils
import com.uv.routinesappuv.webService.RoutinesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.uv.routinesappuv.viewmodel.RoutinesViewModel
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future

class AddExerciseFragment : Fragment() {

    private lateinit var spinner: Spinner
    private lateinit var equipamento: Spinner
    private lateinit var series: Spinner
    private lateinit var repeticiones: Spinner
    private lateinit var descripcion: AutoCompleteTextView
    private lateinit var btnAgregar: Button
    private lateinit var apiService: ApiService
    private lateinit var receivedRutina: Rutina
    private val equipamentoList = listOf(
        "Selecciona un equipamiento",
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
    private val rutinasViewModel: RoutinesViewModel by viewModels()
    private val seriesList = (1..10).map { it.toString() }
    private val repeticionesList = (1..10).map { it.toString() }
    private var exercises = mutableListOf<Ejercicio>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_exercise, container, false)

        // Initialize ApiService
        apiService = ApiUtils.getApiService()

        // Initialize Views
        spinner = view.findViewById(R.id.spinner)
        equipamento = view.findViewById(R.id.Equipamento)
        series = view.findViewById(R.id.series)
        repeticiones = view.findViewById(R.id.repeticiones)
        descripcion = view.findViewById(R.id.autoCompleteTextView)
        btnAgregar = view.findViewById(R.id.button)

        receivedRutina = arguments?.getSerializable("rutina") as Rutina
        exercises = receivedRutina.ejercicios.toMutableList()
        Log.d("received rutina", "routine $receivedRutina")
        // Fetch exercises from API and setup Spinner
        fetchExercises()

        // Setup static spinners
        setupSpinner(equipamento, equipamentoList)
        setupSpinner(series, seriesList)
        setupSpinner(repeticiones, repeticionesList)

        // Configure button click listener
        btnAgregar.setOnClickListener {
            if (isFormValid()) {
                // Handle adding the exercise
                addExercise()
            } else {
                Toast.makeText(requireContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun fetchExercises() {
        apiService.getExercises(0).enqueue(object : Callback<List<RoutinesResponse>> {
            override fun onResponse(
                call: Call<List<RoutinesResponse>>,
                response: Response<List<RoutinesResponse>>
            ) {
                if (response.isSuccessful) {
                    val routinesList = response.body()
                    val exerciseNames = mutableListOf<String>()

                    if (routinesList != null) {
                        // Add exercises to the list
                        exerciseNames.add("Selecciona un ejercicio") // Default option

                        for (routine in routinesList) {
                            exerciseNames.add(routine.name)
                        }

                        // Configure adapter for the Spinner
                        val adapter = ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            exerciseNames
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinner.adapter = adapter
                    }
                }
            }

            override fun onFailure(call: Call<List<RoutinesResponse>>, t: Throwable) {
                // Handle error
            }
        })
    }

    private fun setupSpinner(spinner: Spinner, items: List<String>) {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            items
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun isFormValid(): Boolean {
        return spinner.selectedItemPosition != 0 &&
                equipamento.selectedItemPosition != 0 &&
                series.selectedItemPosition != 0 &&
                repeticiones.selectedItemPosition != 0 &&
                descripcion.text.toString().isNotEmpty()
    }
    private fun generaNuevoId(): Int {
        // Aquí puedes implementar la lógica para generar un nuevo ID único para el ejercicio
        // Por ejemplo, podrías obtener el ID más alto actualmente en la lista de ejercicios y agregar 1
        // O podrías generar un ID aleatorio único
        // En este ejemplo, simplemente se devuelve un número aleatorio
        return (0..999).random()
    }
    private fun getExerciseGifUrl(exerciseName: String): String? {
        val executor = Executors.newSingleThreadExecutor()
        val future: Future<String?> = executor.submit(Callable {
            try {
                val apiService = ApiUtils.getApiService()
                val response = apiService.getExerciseByName(exerciseName).execute()
                if (response.isSuccessful) {
                    val exercises = response.body()
                    if (!exercises.isNullOrEmpty()) {
                        exercises[0].gifUrl // Asumiendo que siempre hay al menos un resultado
                    } else {
                        null
                    }
                } else {
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        })

        return try {
            future.get()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            executor.shutdown()
        }
    }
    private val ejercicios = mutableListOf<Ejercicio>()

    private fun addExercise() {
        val nombreEjercicio = spinner.selectedItem.toString()
        val descripcion = descripcion.text.toString()
        val equipamiento = equipamento.selectedItem.toString()
        val series = series.selectedItem.toString().toIntOrNull() ?: 0
        val repeticiones = repeticiones.selectedItem.toString().toIntOrNull() ?: 0
        val img = getExerciseGifUrl(nombreEjercicio)
        if (nombreEjercicio.isNotEmpty() && descripcion.isNotEmpty() && equipamiento.isNotEmpty()) {
            val ejercicio = Ejercicio(
                id = generaNuevoId(),
                nombre_ejercicio = nombreEjercicio,
                descripcion_ejercicio = descripcion,
                equipamento = equipamiento,
                series = series,
                repeticiones = repeticiones,
                img = img.toString() // URL de la imagen del ejercicio, si es necesario
            )

            exercises.add(ejercicio)
            val updatedRutina = receivedRutina.copy(
                nombre_rutina = receivedRutina.nombre_rutina,
                descripcion_rutina = receivedRutina.descripcion_rutina,
                ejercicios = exercises
            )

            rutinasViewModel.updateRutina(updatedRutina)
//            findNavController().navigate(
//              //  R.id.action_editExerciseFragment_to_editRoutineFragment
//            )
            val bundle = Bundle().apply {
                putSerializable("clave", updatedRutina)
            }
            findNavController().navigate(
                R.id.action_editExerciseFragment_to_editRoutineFragment,
                bundle
            )

            Log.d("rutinas viejas", "jaj ${receivedRutina.ejercicios}")
            Log.d("rutinas nuevas", "jaj ${exercises}")
            Toast.makeText(requireContext(), "Ejercicio agregado", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
        }
    }
}