package com.uv.routinesappuv.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.uv.routinesappuv.R
import com.uv.routinesappuv.webService.ApiService
import com.uv.routinesappuv.webService.ApiUtils
import com.uv.routinesappuv.webService.RoutinesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddExerciseFragment : Fragment() {

    private lateinit var opciones: Spinner
    private lateinit var spinner2: Spinner
    private lateinit var spinner3: Spinner
    private lateinit var autoCompleteTextView: AutoCompleteTextView
    private lateinit var spinner4: Spinner
    private lateinit var btnAgregar: Button
    private lateinit var apiService: ApiService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_exercise, container, false)

        // Initialize ApiService
        apiService = ApiUtils.getApiService()




        // Initialize Views
        opciones = view.findViewById(R.id.spinner)
        spinner2 = view.findViewById(R.id.spinner2)
        spinner3 = view.findViewById(R.id.spinner3)
        autoCompleteTextView = view.findViewById(R.id.autoCompleteTextView)
        spinner4 = view.findViewById(R.id.spinner4)
        btnAgregar = view.findViewById(R.id.button)

        // Fetch exercises from API and setup Spinner
        fetchExercises()

        // Configure Toolbar
        //setupToolbar(view)

        // Configure button click listener
        btnAgregar.setOnClickListener {
            // Handle button click
        }

        return view
    }

    private fun fetchExercises() {
        apiService.getExercises().enqueue(object : Callback<List<RoutinesResponse>> {
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
                        opciones.adapter = adapter
                    }
                }
            }

            override fun onFailure(call: Call<List<RoutinesResponse>>, t: Throwable) {
                // Handle error
            }
        })
    }

//    private fun setupToolbar(view: View) {
//        val toolbar = view.findViewById<Toolbar>(R.id.toolbar_edit)
//        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
//        toolbar.setNavigationOnClickListener {
//            // Navigate back to the previous fragment
//            findNavController().navigateUp()
//        }
//    }
}
