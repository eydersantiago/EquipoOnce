package com.uv.routinesappuv.repository

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.uv.routinesappuv.model.Ejercicio
import com.uv.routinesappuv.model.Rutina
import kotlinx.coroutines.tasks.await

class ExercisesRepository(val context: Context) {
    private val db = FirebaseFirestore.getInstance()

    suspend fun getExercises(): MutableList<Ejercicio> {
        val exercises = mutableListOf<Ejercicio>()
        try {
            val result = db.collection("ejercicios").get().await()
            for (document in result) {
                val id = document.id
                val nombre = document.getString("nombre_ejercicio") ?: ""
                val descripcion = document.getString("descripcion_ejercicio") ?: ""
                val equipamento = document.getString("equipamento") ?: ""
                val repeticiones = document.getString("repeticiones")?.toInt() ?: 0
                val series = document.getString("series")?.toInt() ?: 0
                val ejercicio = Ejercicio(id, nombre, descripcion, equipamento, repeticiones, series)
                exercises.add(ejercicio)
                Log.d("Firestore", "${document.id} => ${document.data}")
            }
        } catch (exception: Exception) {
            Log.w("Firestore", "Error getting documents: ", exception)
        }
        return exercises
    }
}
