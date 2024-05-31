package com.uv.routinesappuv.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.uv.routinesappuv.model.Ejercicio
import com.uv.routinesappuv.model.Rutina
import com.google.firebase.firestore.ktx.toObjects
class RutinasRepository {
    private val db = FirebaseFirestore.getInstance()

    fun getRutinas(callback: (List<Rutina>) -> Unit) {
        db.collection("rutinas").get().addOnSuccessListener { result ->
            val rutinas = result.toObjects<Rutina>()
            callback(rutinas)
        }
    }
    fun getEjercicios(rutinaId: String, callback: (List<Ejercicio>) -> Unit) {
        db.collection("rutinas").document(rutinaId).collection("ejercicios").get()
            .addOnSuccessListener { result ->
                val ejercicios = result.toObjects<Ejercicio>()
                callback(ejercicios)
            }
    }


}