package com.uv.routinesappuv.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.uv.routinesappuv.R
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
        binding.btnDeleteRoutine.setOnClickListener {
            deleteRutina()
        }

        // Uncomment and implement this if you want to add edit functionality
        // binding.btnEditRoutine.setOnClickListener {
        //     val bundle = Bundle()
        //     bundle.putSerializable("dataCita", receivedCita)
        //     val detalleCitaFragment = DetalleCitaFragment()
        //     detalleCitaFragment.arguments = bundle
        //     findNavController().navigate(
        //         R.id.action_itemDetailsFragment_to_itemEditFragment,
        //         bundle
        //     )
        // }
    }

    private fun dataRutina() {
        val receivedBundle = arguments
        receivedRutina = receivedBundle?.getSerializable("clave") as Rutina

        val recycler = binding.recyclerview
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = ExercisesAdapter(receivedRutina.ejercicios)

        binding.titleDescripcion.text = receivedRutina.descripcion_rutina

        // Accede y actualiza el TextView dentro del layout incluido
        val includedToolbarView = binding.contentToolbarEditar.root
        val titleTextViewEdit = includedToolbarView.findViewById<TextView>(R.id.titleTextViewEdit)
        titleTextViewEdit.text = receivedRutina.nombre_rutina
    }

    private fun deleteRutina() {
        rutinasViewModel.deleteRutina(receivedRutina.id) // Use the instance method
        findNavController().popBackStack()

        Toast.makeText(context, "Rutina eliminada !!", Toast.LENGTH_SHORT).show()
    }
}
