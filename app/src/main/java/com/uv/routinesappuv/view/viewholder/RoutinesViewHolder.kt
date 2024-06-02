package com.uv.routinesappuv.view.viewholder

import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uv.routinesappuv.R
import com.uv.routinesappuv.databinding.ItemExerciseBinding
import com.uv.routinesappuv.model.Ejercicio
import com.uv.routinesappuv.utils.Constants
import com.uv.routinesappuv.webService.ApiService
import com.uv.routinesappuv.webService.ImageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RoutinesViewHolder(binding: ItemExerciseBinding) : RecyclerView.ViewHolder(binding.root){

    val bindingExerciseItem = binding

    fun setItemExercise(exercise: Ejercicio){
        bindingExerciseItem.tvNombreEjercicio.text = exercise.nombre_ejercicio
        bindingExerciseItem.tvDescripcion.text = exercise.descripcion_ejercicio
        bindingExerciseItem.tvEquipamento.text = exercise.equipamento
        bindingExerciseItem.tvSeries.text = "${exercise.series}"
        bindingExerciseItem.tvRepeticiones.text = "${exercise.repeticiones}"

//        bindingExerciseItem.cvRutina.setOnClickListener{
//            val bundle = Bundle()
//            bundle.putSerializable("clave", exercise)
//        }

    }



}