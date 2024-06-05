package com.uv.routinesappuv.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.uv.routinesappuv.repository.RutinasRepository

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    val context = getApplication<Application>()
    var repository = RutinasRepository(context)

    //registerUser se comunica con el repository
    fun registerUser(email: String, pass: String, isRegister: (Boolean) -> Unit) {
        repository.registerUser(email, pass) { response ->
            isRegister(response)
        }
    }

    fun loginUser(email: String, pass: String, isLogin: (Boolean) -> Unit) {
        repository.loginUser(email, pass) { response ->
            isLogin(response)
        }
    }

    fun sesion(email: String?, isEnableView: (Boolean) -> Unit) {
        if (email != null) {
            isEnableView(true)
        } else {
            isEnableView(false)
        }
    }
}