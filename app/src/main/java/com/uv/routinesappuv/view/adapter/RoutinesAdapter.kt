package com.uv.routinesappuv.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.uv.routinesappuv.databinding.ItemRutinaBinding
import com.uv.routinesappuv.model.Rutina
import com.uv.routinesappuv.view.viewholder.RoutinesViewHolder

class RoutinesAdapter(private val listRutina: MutableList<Rutina>, private val navController: NavController):
    RecyclerView.Adapter<RoutinesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutinesViewHolder {
        val binding = ItemRutinaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RoutinesViewHolder(binding, navController)
    }

    override fun getItemCount(): Int {
        return listRutina.size
    }

    override fun onBindViewHolder(holder: RoutinesViewHolder, position: Int) {
        val rutina = listRutina[position]
        holder.setItemCita(rutina)
    }
}