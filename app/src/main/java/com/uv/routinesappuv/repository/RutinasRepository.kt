package com.uv.routinesappuv.repository

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.uv.routinesappuv.model.Ejercicio
import com.uv.routinesappuv.model.Rutina
import kotlinx.coroutines.tasks.await
import com.google.firebase.auth.FirebaseAuth

class RutinasRepository(val context: Context) {
    private val db = FirebaseFirestore.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()

    suspend fun getRutinas(userEmail: String): MutableList<Rutina> {
        val rutinas = mutableListOf<Rutina>()
        try {
            val result = db.collection("rutina")
                .whereEqualTo("user_mail", userEmail)
                .get()
                .await()
            for (document in result) {
                val nombre = document.getString("nombre_rutina") ?: ""
                val descripcion = document.getString("descripcion_rutina") ?: ""

                // Obtener el arreglo de ejercicios
                val ejercicios = mutableListOf<Ejercicio>()
                val ejerciciosList = document.get("ejercicios") as? List<Map<String, Any>> ?: emptyList()
                for (ejercicioMap in ejerciciosList) {
                    val ejercicioId = (ejercicioMap["id"] as? Long)?.toInt() ?: 0
                    val ejercicioNombre = ejercicioMap["nombre_ejercicio"] as? String ?: ""
                    val ejercicioDescripcion = ejercicioMap["descripcion_ejercicio"] as? String ?: ""
                    val equipamento = ejercicioMap["equipamento"] as? String ?: ""
                    val series = (ejercicioMap["series"] as? Long)?.toInt() ?: 0
                    val repeticiones = (ejercicioMap["repeticiones"] as? Long)?.toInt() ?: 0

                    val ejercicio = Ejercicio(ejercicioId, ejercicioNombre, ejercicioDescripcion, equipamento, series, repeticiones)
                    ejercicios.add(ejercicio)
                }

                val rutina = Rutina(nombre, descripcion, ejercicios)
                rutinas.add(rutina)
                Log.d("Firestore", "${document.id} => ${document.data}")
            }
        } catch (exception: Exception) {
            Log.w("Firestore", "Error getting documents: ", exception)
        }
        return rutinas
    }

    // Método para guardar la rutina
    suspend fun saveRoutine(rutina: Rutina) {
        try {
            db.collection("rutina").add(rutina).await()
            Log.d("Firestore", "Rutina guardada exitosamente")
        } catch (exception: Exception) {
            Log.w("Firestore", "Error al guardar la rutina: ", exception)
        }
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
