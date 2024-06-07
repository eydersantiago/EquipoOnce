package com.uv.routinesappuv.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.uv.routinesappuv.R
import com.uv.routinesappuv.databinding.FragmentEditRoutineBinding
import com.uv.routinesappuv.model.Rutina
import com.uv.routinesappuv.view.adapter.ExercisesAdapter
import com.uv.routinesappuv.viewmodel.RoutinesViewModel

class EditRoutineFragment : Fragment() {
    private lateinit var binding: FragmentEditRoutineBinding

    private val rutinasViewModel: RoutinesViewModel by viewModels()
    private lateinit var receivedRutina: Rutina

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditRoutineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        dataRutina()
        controladores()
    }

    private fun controladores() {
        binding.btnEdit.setOnClickListener {
            editRutina()
        }
        binding.btnNuevoEjercicio.setOnClickListener{
            val bundle = Bundle()
            bundle.putSerializable("rutina", receivedRutina) // Pasar toda la rutina
            findNavController().navigate(
                R.id.action_fragment_edit_routine_to_fragment_edit_exercise,
                bundle
            )
        }
        binding.contentToolbarEditar.toolbarEdit.setNavigationOnClickListener { onBackPressed() }
    }

    private fun onBackPressed() {
        findNavController().navigateUp()
    }

    private fun dataRutina() {
        val receivedBundle = arguments
        receivedRutina = receivedBundle?.getSerializable("clave") as Rutina

        binding.etNombreRutina.setText(receivedRutina.nombre_rutina)
        binding.etDescripcion.setText(receivedRutina.descripcion_rutina)

        val recycler = binding.recyclerview
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = ExercisesAdapter(receivedRutina.ejercicios)
    }

    private fun editRutina() {
        val updatedRutina = receivedRutina.copy(
            nombre_rutina = binding.etNombreRutina.text.toString(),
            descripcion_rutina = binding.etDescripcion.text.toString()
        )

        rutinasViewModel.updateRutina(updatedRutina)
        findNavController().navigate(
            R.id.action_fragment_edit_routine_to_fragment_home_routine
        )
    }


}