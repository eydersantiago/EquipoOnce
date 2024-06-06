package com.uv.routinesappuv.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.uv.routinesappuv.view.viewholder.ExercisesViewHolder

import com.uv.routinesappuv.databinding.ItemExerciseBinding
import com.uv.routinesappuv.model.Ejercicio

class ExercisesAdapter(private val listExercises: MutableList<Ejercicio>): RecyclerView.Adapter<ExercisesViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercisesViewHolder {
        val binding = ItemExerciseBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ExercisesViewHolder(binding)
    }

    //retorno el tamaño de la lista
    override fun getItemCount(): Int {
        return listExercises.size
    }

    //detecta en dónde hice touch
    override fun onBindViewHolder(holder: ExercisesViewHolder, position: Int) {
        val inventory = listExercises[position]
        holder.setItemExercise(inventory)
    }

}