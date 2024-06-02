package com.uv.routinesappuv.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.uv.routinesappuv.model.Ejercicio
import com.uv.routinesappuv.model.Rutina
import com.google.firebase.firestore.ktx.toObjects
class RutinasRepository {
    private val db = FirebaseFirestore.getInstance()
    val getListLiveData: MutableLiveData<List<Rutina>> by lazy {
        MutableLiveData<List<Rutina>>()
    }
    fun getRutinas() {
        val docRef = db.collection("rutina")
        docRef.get().addOnSuccessListener {
            val rutinas = ArrayList<Rutina>()
            for (item in it.documents) {
                val id = "9"
                val nombre = item.data!!["nombre_rutina"] as String
                val descripcion = item.data!!["descripcion_rutina"] as String

                val rutina = Rutina(id, nombre, descripcion)
                rutinas.add(rutina)
            }

            getListLiveData.postValue(rutinas)
        }.addOnFailureListener {
            Log.d("get", it.localizedMessage!!)
            getListLiveData.postValue(null)
        }
    }




}