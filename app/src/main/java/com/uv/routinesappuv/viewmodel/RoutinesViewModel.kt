package com.uv.routinesappuv.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.uv.routinesappuv.model.Ejercicio
import com.uv.routinesappuv.model.Rutina
import com.uv.routinesappuv.repository.RutinasRepository
import kotlinx.coroutines.launch

class RoutinesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = RutinasRepository()
    private val _rutinas = MutableLiveData<List<Rutina>>()
    val rutinas: LiveData<List<Rutina>> get() = _rutinas

    private val _ejercicios = MutableLiveData<List<Ejercicio>>()
    val ejercicios: LiveData<List<Ejercicio>> get() = _ejercicios


    fun fetchRutinas() {
        viewModelScope.launch {

            try {
                repository.getRutinas()
            } catch (e: Exception) {

            }

        }
    }

}

