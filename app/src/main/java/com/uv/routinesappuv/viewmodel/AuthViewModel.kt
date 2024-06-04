package com.uv.routinesappuv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class AuthViewModel : ViewModel() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val _authenticationResult = MutableLiveData<Boolean>()
    val authenticationResult: LiveData<Boolean>
        get() = _authenticationResult

    fun signInWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authenticationResult.value = true // Autenticación exitosa
                } else {
                    _authenticationResult.value = false // Error de autenticación
                }
            }
    }
}
