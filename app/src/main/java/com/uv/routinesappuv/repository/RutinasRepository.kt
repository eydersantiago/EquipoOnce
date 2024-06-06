package com.uv.routinesappuv.repository

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.uv.routinesappuv.model.Rutina
import kotlinx.coroutines.tasks.await
import com.google.firebase.auth.FirebaseAuth

class RutinasRepository(val context: Context) {
    private val db = FirebaseFirestore.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()

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

    fun registerUser(email: String, pass: String, isRegisterComplete: (Boolean) -> Unit) {
        Log.e("testregistro", "$email-------$pass")
        if (email.isNotEmpty() && pass.isNotEmpty()) {
            Log.e("testregistro", "entre al if")
            firebaseAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener {task ->
                    Log.e("testregistro", "$task")
                    if (task.isSuccessful) {
                        isRegisterComplete(true)
                    } else {
                        Log.e("testregistro", "Error al registrar: ${task.exception}")
                        isRegisterComplete(false)
                    }
                }
        } else {
            Log.e("testregistro", "entre al else")
            isRegisterComplete(false)
        }
    }

    fun loginUser(email: String, pass: String, isLoginComplete: (Boolean) -> Unit) {
        if (email.isNotEmpty() && pass.isNotEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        isLoginComplete(true)
                    } else {
                        isLoginComplete(false)
                    }
                }
        } else {
            isLoginComplete(false)
        }
    }
}
