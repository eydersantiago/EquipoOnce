package com.uv.routinesappuv.view.viewholder

import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uv.routinesappuv.R
import com.uv.routinesappuv.utils.Constants
import com.uv.routinesappuv.webService.ApiService
import com.uv.routinesappuv.webService.ImageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.uv.routinesappuv.databinding.ItemRutinaBinding
import com.uv.routinesappuv.model.Rutina

class RoutinesViewHolder(binding: ItemRutinaBinding, navController: NavController):
    RecyclerView.ViewHolder(binding.root) {
    val bindingItem = binding
    val navController = navController

    fun setItemRutina(rutina: Rutina) {


        bindingItem.tvNombreRutina.text = rutina.nombre_rutina
        bindingItem.tvDescripcion.text = "${rutina.descripcion_rutina}"

        bindingItem.cvRutina.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("clave", rutina)
            navController.navigate(
                R.id.action_add_routine, bundle
            )
        }
    }


}