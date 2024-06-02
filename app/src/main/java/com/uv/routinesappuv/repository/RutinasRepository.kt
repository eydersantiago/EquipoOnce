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
        docRef.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("Firestore", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Firestore", "Error getting documents: ", exception)
            }
    }




}