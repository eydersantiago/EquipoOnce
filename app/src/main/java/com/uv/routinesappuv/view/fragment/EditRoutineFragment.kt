package com.uv.routinesappuv.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.uv.routinesappuv.R
import com.uv.routinesappuv.databinding.FragmentEditRoutineBinding
import com.uv.routinesappuv.model.Ejercicio
import com.uv.routinesappuv.view.adapter.ExercisesAdapter
import com.uv.routinesappuv.view.adapter.RoutinesAdapter

class EditRoutineFragment : Fragment() {
    private lateinit var binding: FragmentEditRoutineBinding
    //private lateinit var receivedEjercicio: Ejercicio

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditRoutineBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //dataRoutines()
        controladores()

    }

//    private fun dataRoutines(){
//        val receivedBundle = arguments
//        receivedEjercicio = receivedBundle?.getSerializable("dataEjercicio") as Ejercicio
//        binding..setText(receivedEjercicio.name)
//        binding.etPrice.setText(receivedEjercicio.price.toString())
//        binding.etQuantity.setText(receivedEjercicio.quantity.toString())
//
//    }

    private fun controladores(){
        recycler()
    }

    fun recycler(){
        var listExercises = mutableListOf(
            Ejercicio(1, "abdominales", "fortalecer_abdomen", "mancuernas",2, 3),
            Ejercicio(2, "sentadillas", "fortalecer_piernas", "máquina",4, 5)
        )

        val recycler = binding.recyclerview
        recycler.layoutManager = LinearLayoutManager(context)
        val adapter = ExercisesAdapter(listExercises)
        recycler.adapter = adapter
        adapter.notifyDataSetChanged()
    }




}