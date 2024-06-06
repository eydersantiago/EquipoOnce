package com.uv.routinesappuv.repository

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.uv.routinesappuv.model.Ejercicio
import com.uv.routinesappuv.model.Rutina
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ExercisesViewModel(application: Application) : AndroidViewModel(application) {

    val context = getApplication<Application>()
    private val repository = RutinasRepository(context)

    private val _rutinas = MutableLiveData<MutableList<Rutina>>()
    val rutinas: LiveData<MutableList<Rutina>> get() = _rutinas

    private val _ejercicios = MutableLiveData<List<Ejercicio>>()
    val ejercicios: LiveData<List<Ejercicio>> get() = _ejercicios


    fun fetchRutinas() {
        viewModelScope.launch {
            try {
                val ejercicios = repository.getRutinas()
                _ejercicios.value = ejercicios
            } catch (e: Exception) {
                // Handle error
            }
        }
    }


}


