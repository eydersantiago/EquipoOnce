package com.uv.routinesappuv.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.uv.routinesappuv.R

class EditExerciseFragment : Fragment() {

    lateinit var opciones: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_exercise, container, false)
        opciones = view.findViewById(R.id.spinner)

        val lista = listOf("opcion1", "opcion2", "opcion3")
        val adaptador = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, lista)
        opciones.adapter = adaptador

        // Configurar Toolbar
        setupToolbar(view)

        return view
    }

    private fun setupToolbar(view: View) {
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar_edit)
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            // Navegar de vuelta al fragmento fragment_edit_routine
            findNavController().navigateUp()
        }
    }
}
