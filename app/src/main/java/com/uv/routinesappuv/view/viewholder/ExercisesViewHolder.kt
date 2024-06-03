package com.uv.routinesappuv.view.viewholder

import android.os.Bundle
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.uv.routinesappuv.R
import com.uv.routinesappuv.databinding.ItemExerciseBinding
import com.uv.routinesappuv.model.Ejercicio

class ExercisesViewHolder(binding: ItemExerciseBinding) : RecyclerView.ViewHolder(binding.root){

    val bindingExerciseItem = binding

    fun setItemExercise(exercise: Ejercicio){
        bindingExerciseItem.tvNombreEjercicio.text = exercise.nombre_ejercicio
        bindingExerciseItem.tvDescripcion.text = exercise.descripcion_ejercicio
        bindingExerciseItem.tvEquipamento.text = exercise.equipamento
        bindingExerciseItem.tvSeries.text = "${exercise.series}"
        bindingExerciseItem.tvRepeticiones.text = "${exercise.repeticiones}"

//        bindingExerciseItem.cvRutina.setOnClickListener{
//            val bundle = Bundle()
//            bundle.putSerializable("clave", exercise)navController.navigate(
//                    R.id.action_add_routine, bundle
//            )
//        }

    }



}
