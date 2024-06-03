package com.uv.routinesappuv.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.uv.routinesappuv.R

class EditExerciseFragment : Fragment() {

    lateinit var opciones: Spinner
    lateinit var texto: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_exercise, container, false)
        opciones = view.findViewById(R.id.spinner)
        texto = view.findViewById(R.id.editTextText)

        val lista = listOf("opcion1", "opcion2", "opcion3")
        val adaptador = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, lista)
        opciones.adapter = adaptador

        return view
    }
}
