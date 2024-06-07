package com.uv.routinesappuv.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uv.routinesappuv.databinding.ItemExerciseBinding
import com.uv.routinesappuv.model.Ejercicio
import com.uv.routinesappuv.view.viewholder.ExercisesViewHolder

class ExercisesAdapter(private val ejercicios: List<Ejercicio>) : RecyclerView.Adapter<ExercisesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercisesViewHolder {
        val binding = ItemExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExercisesViewHolder(binding)
    }

    override fun getItemCount(): Int = ejercicios.size

    override fun onBindViewHolder(holder: ExercisesViewHolder, position: Int) {
        holder.bind(ejercicios[position])
    }
}
