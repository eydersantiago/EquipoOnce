package com.uv.routinesappuv.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.uv.routinesappuv.databinding.FragmentDetailRoutineBinding
import com.uv.routinesappuv.model.Rutina
import com.uv.routinesappuv.view.adapter.ExercisesAdapter
import com.uv.routinesappuv.viewmodel.RoutinesViewModel
import com.uv.routinesappuv.R
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
        setupToolbar()
    }

    private fun controladores() {
        // Implementa tus controladores aquí si es necesario
    }

    private fun dataRutina() {
        val receivedBundle = arguments
        val image = receivedBundle?.getSerializable("imagen")
        receivedRutina = receivedBundle?.getSerializable("clave") as Rutina
        val recycler = binding.recyclerview
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = ExercisesAdapter(receivedRutina.ejercicios)

        binding.titleDescripcion.text = receivedRutina.descripcion_rutina

        // Aquí se actualiza el TextView del Toolbar
        val toolbarTitle = view?.findViewById<TextView>(R.id.titleTextViewEdit)
        toolbarTitle?.text = receivedRutina.nombre_rutina // Suponiendo que el nombre de la rutina está en esta variable
    }
    private fun setupToolbar() {
        binding.contentToolbarEditar.toolbarEdit.setNavigationOnClickListener { onBackPressed() }
    }
    private fun onBackPressed() {
        findNavController().navigate(R.id.action_fragment_detail_routine_to_fragment_home_routine)
    }
}
