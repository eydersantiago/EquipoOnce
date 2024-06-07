package com.uv.routinesappuv.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.uv.routinesappuv.model.Ejercicio
import com.uv.routinesappuv.model.Rutina
import com.uv.routinesappuv.repository.RutinasRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.CancellationException

class RoutinesViewModel(application: Application) : AndroidViewModel(application) {
    val context = getApplication<Application>()
    private val repository = RutinasRepository(context)

    private val _rutinas = MutableLiveData<MutableList<Rutina>>()
    val rutinas: LiveData<MutableList<Rutina>> get() = _rutinas

    private val _ejercicios = MutableLiveData<List<Ejercicio>>()
    val ejercicios: LiveData<List<Ejercicio>> get() = _ejercicios

    private val _routineDetail = MutableLiveData<Rutina>()
    val routineDetail: LiveData<Rutina> get() = _routineDetail

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

    fun loadRoutine(routineId: String) {
        viewModelScope.launch {
            try {
                val rutina = repository.getRoutineById(routineId)
                _routineDetail.value = rutina ?: throw NullPointerException("Routine is null")
            } catch (e: Exception) {
                // Handle error
                Log.e("RoutinesViewModel", "Error loading routine: ${e.message}")
            }
        }
    }

    fun deleteRutina(rutinaId: String) {
        viewModelScope.launch {
            try {
                repository.deleteRoutine(rutinaId)
                _rutinas.value = _rutinas.value?.filter { it.id != rutinaId }?.toMutableList()
            } catch (e: CancellationException) {
                // Handle job cancellation specifically
                Log.w("RoutinesViewModel", "Job was cancelled: ${e.message}")
            } catch (e: Exception) {
                // Handle other exceptions
                Log.w("RoutinesViewModel", "Error deleting routine: ${e.message}")
            }
        }
    }

    fun updateRutina(rutina: Rutina) {
        viewModelScope.launch {
            try {
                repository.updateRoutine(rutina)
                fetchRutinas(rutina.user_mail) // Refresh the list of routines after updating
            } catch (e: CancellationException) {
                // Handle job cancellation specifically
                Log.w("RoutinesViewModel", "Job was cancelled: ${e.message}")
            } catch (e: Exception) {
                // Handle other exceptions
                Log.w("RoutinesViewModel", "Error updating routine: ${e.message}")
            }
        }
    }
}

