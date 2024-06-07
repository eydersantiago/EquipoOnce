package com.uv.routinesappuv.view.fragment

import android.os.Bundle
import android.telecom.Call
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.uv.routinesappuv.R
import com.uv.routinesappuv.databinding.FragmentAddRoutineBinding
import com.uv.routinesappuv.databinding.FragmentEditExerciseBinding
import com.uv.routinesappuv.databinding.FragmentEditRoutineBinding
import com.uv.routinesappuv.model.Ejercicio
import com.uv.routinesappuv.model.Rutina
import com.uv.routinesappuv.viewmodel.RoutinesViewModel
import com.uv.routinesappuv.webService.ApiService
import com.uv.routinesappuv.webService.ApiUtils
import com.uv.routinesappuv.webService.RoutinesResponse
import retrofit2.Response
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future
import javax.security.auth.callback.Callback

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [EditExerciseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditExerciseFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var binding: FragmentEditExerciseBinding

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditExerciseBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        // Inflate the layout for this fragment
        apiService = ApiUtils.getApiService()

        // Initialize Views


        receivedRutina = arguments?.getSerializable("rutina") as Rutina
        exercises = receivedRutina.ejercicios.toMutableList()
        Log.d("received rutina", "routine $receivedRutina")
        // Fetch exercises from API and setup Spinner
        fetchExercises()

        // Setup static spinners
        setupSpinner()


        // Configure button click listener
        binding.btnAgregar.setOnClickListener {
            if (isFormValid()) {
                // Handle adding the exercise
                addExercise()
            } else {
                Toast.makeText(requireContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }
    private val routinesList = mutableListOf<String>()
    private fun fetchExercises() {
        apiService.getExercises(0).enqueue(object : retrofit2.Callback<List<RoutinesResponse>> {
            override fun onResponse(
                call: retrofit2.Call<List<RoutinesResponse>>,
                response: Response<List<RoutinesResponse>>
            ) {
                if (response.isSuccessful) {
                    val routinesList = response.body()
                    if (routinesList != null) {
                        // Clear the list and add the default option
                        this@EditExerciseFragment.routinesList.clear()
                        this@EditExerciseFragment.routinesList.add("Nombre ejercicio")

                        // Add exercise names from the response
                        for (routine in routinesList) {
                            Log.d("Routine", "Name: ${routine.name}, Body Part: ${routine.bodyPart}")
                            this@EditExerciseFragment.routinesList.add(routine.name)

                        }

                        // Update the spinner with the new data
                        setupSpinner()
                    } else {
                        Log.e("fetchExercises", "Response body is null")
                    }
                } else {
                    Log.e("fetchExercises", "Failed to fetch exercises. Response code: ${response.code()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<List<RoutinesResponse>>, t: Throwable) {
                Log.e("fetchExercises", "Error fetching exercises", t)
                Toast.makeText(requireContext(), "Error fetching exercises", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupSpinner() {
        val exercisesAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, routinesList)
        exercisesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = exercisesAdapter
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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.contentToolbarEdit.toolbarEdit.setNavigationOnClickListener { onBackPressed() }
    }
    private fun onBackPressed() {
        findNavController().navigateUp()
    }
    private fun isFormValid(): Boolean {
        return binding.spinner.selectedItemPosition != 0 &&
                binding.inputEquipamiento.selectedItemPosition != 0 &&
                binding.etSeries.text.toString().isNotEmpty() &&
                binding.etRepeticiones.text.toString().isNotEmpty() &&
                binding.etDescripcion.text.toString().isNotEmpty()
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
        val nombreEjercicio = binding.spinner.selectedItem.toString()
        val descripcion = binding.etDescripcion.text.toString()
        val equipamiento = binding.inputEquipamiento.selectedItem.toString()
        val series = binding.etSeries.text.toString().toIntOrNull() ?: 0
        val repeticiones = binding.etRepeticiones.text.toString().toIntOrNull() ?: 0
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


