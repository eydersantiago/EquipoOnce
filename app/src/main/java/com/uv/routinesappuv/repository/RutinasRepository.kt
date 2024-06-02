package com.uv.routinesappuv.repository

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.uv.routinesappuv.model.Rutina
import kotlinx.coroutines.tasks.await

class RutinasRepository(val context: Context) {
    private val db = FirebaseFirestore.getInstance()

    suspend fun getRutinas(): MutableList<Rutina> {
        val rutinas = mutableListOf<Rutina>()
        try {
            val result = db.collection("rutina").get().await()
            for (document in result) {
                val id = document.id
                val nombre = document.getString("nombre_rutina") ?: ""
                val descripcion = document.getString("descripcion_rutina") ?: ""
                val rutina = Rutina(id, nombre, descripcion)
                rutinas.add(rutina)
                Log.d("Firestore", "${document.id} => ${document.data}")
            }
        } catch (exception: Exception) {
            Log.w("Firestore", "Error getting documents: ", exception)
        }
        return rutinas
    }
}
