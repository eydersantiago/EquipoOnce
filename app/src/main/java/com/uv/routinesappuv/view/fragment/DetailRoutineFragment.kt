package com.uv.routinesappuv.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.uv.routinesappuv.databinding.FragmentDetailRoutineBinding
import com.uv.routinesappuv.model.Rutina
import com.uv.routinesappuv.view.adapter.ExercisesAdapter
import com.uv.routinesappuv.viewmodel.RoutinesViewModel

class DetailRoutineFragment : Fragment() {
    private lateinit var binding: FragmentDetailRoutineBinding

    private val rutinasViewModel: RoutinesViewModel by viewModels()
    private lateinit var receivedRutina: Rutina

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailRoutineBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataRutina()
        controladores()
    }

    private fun controladores() {
        // Implementa tus controladores aquí si es necesario
    }

    private fun dataRutina() {
        val receivedBundle = arguments

        receivedRutina = receivedBundle?.getSerializable("clave") as Rutina
        val recycler = binding.recyclerview
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = ExercisesAdapter(receivedRutina.ejercicios)

        binding.titleDescripcion.text = receivedRutina.descripcion_rutina
    }
}
