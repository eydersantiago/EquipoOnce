package com.uv.routinesappuv.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.uv.routinesappuv.databinding.ItemExerciseBinding
import com.uv.routinesappuv.model.Ejercicio

class ExercisesViewHolder(private val binding: ItemExerciseBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(ejercicio: Ejercicio) {
        binding.tvNombreEjercicio.text = ejercicio.nombre_ejercicio
        binding.tvDescripcion.text = ejercicio.descripcion_ejercicio
        binding.tvEquipamento.text = ejercicio.equipamento
        binding.tvSeries.text = "Series: ${ejercicio.series}"
        binding.tvRepeticiones.text = "Repeticiones: ${ejercicio.repeticiones}"
    }
}
