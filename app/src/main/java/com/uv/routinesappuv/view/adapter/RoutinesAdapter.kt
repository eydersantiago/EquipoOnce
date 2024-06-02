package com.uv.routinesappuv.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.uv.routinesappuv.databinding.ItemExerciseBinding
import com.uv.routinesappuv.model.Ejercicio
import com.uv.routinesappuv.view.viewholder.RoutinesViewHolder

class RoutinesAdapter(private val listExercises: MutableList<Ejercicio>): RecyclerView.Adapter<RoutinesViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutinesViewHolder {
        val binding = ItemExerciseBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return RoutinesViewHolder(binding)
    }

    //retorno el tamaño de la lista
    override fun getItemCount(): Int {
        return listExercises.size
    }

    //detecta en dónde hice touch
    override fun onBindViewHolder(holder: RoutinesViewHolder, position: Int) {
        val inventory = listExercises[position]
        holder.setItemExercise(inventory)
    }

}