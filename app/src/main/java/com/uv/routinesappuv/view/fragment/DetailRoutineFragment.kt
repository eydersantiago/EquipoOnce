package com.uv.routinesappuv.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
//import com.google.firebase.auth.FirebaseAuth
import com.uv.routinesappuv.R
import com.uv.routinesappuv.databinding.FragmentDetailRoutineBinding
import com.uv.routinesappuv.model.Rutina
import com.uv.routinesappuv.view.adapter.ExercisesAdapter
//import com.uv.routinesappuv.view.adapter.RoutinesAdapter
import com.uv.routinesappuv.viewmodel.RoutinesViewModel
//import androidx.lifecycle.Observer

class DetailRoutineFragment : Fragment() {
    private lateinit var binding: FragmentDetailRoutineBinding

    private val rutinasViewModel: RoutinesViewModel by viewModels()
    private lateinit var receivedRutina: Rutina
    private val routinesViewModel: RoutinesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailRoutineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        dataRutina()
        controladores()

    }

    private fun controladores() {
        binding.btnDeleteRoutine.setOnClickListener {
            deleteRutina()
        }

        binding.btnEditRoutine.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("clave", receivedRutina)
            }
            findNavController().navigate(
                R.id.action_fragment_detail_routine_to_fragment_edit_routine,
                bundle
            )
           // observadorViewModel();
        }
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
        rutinasViewModel.deleteRutina(receivedRutina.id)
        findNavController().popBackStack()
    }

//    private fun observadorViewModel() {
//        observerListRutinas()
//
//    }
//
//    private fun observerListRutinas() {
//        val email = getEmail()
//
//        routinesViewModel.fetchRutinas(email.toString())
//        routinesViewModel.rutinas.observe(viewLifecycleOwner) { listRutinas ->
//            val recycler = binding.recyclerview
//            val layoutManager = LinearLayoutManager(context)
//            recycler.layoutManager = layoutManager
//            val adapter = RoutinesAdapter(listRutinas, findNavController())
//            recycler.adapter = adapter
//            adapter.notifyDataSetChanged()
//        }
//
//        routinesViewModel.rutinas.observe(viewLifecycleOwner, Observer { rutinas ->
//            // Log each rutina or update your UI here
//            rutinas.forEach { rutina ->
//                Log.d("HomeRoutineFragment", "Rutina: $rutina")
//            }
//        })
//    }
//
//    private fun getEmail(): String? {
//        val auth = FirebaseAuth.getInstance()
//        val user = auth.currentUser
//        return user?.email
//    }




}
