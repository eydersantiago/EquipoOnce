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
    val context = getApplication<Application>()
    private val repository = RutinasRepository(context)

    private val _rutinas = MutableLiveData<MutableList<Rutina>>()
    val rutinas: LiveData<MutableList<Rutina>> get() = _rutinas

    private val _ejercicios = MutableLiveData<List<Ejercicio>>()
    val ejercicios: LiveData<List<Ejercicio>> get() = _ejercicios

    fun fetchRutinas(mail: String) {
        viewModelScope.launch {
            try {
                val rutinas = repository.getRutinas(mail)
                _rutinas.value = rutinas
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
