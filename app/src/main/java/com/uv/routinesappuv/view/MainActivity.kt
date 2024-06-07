package com.uv.routinesappuv.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.FirebaseApp
import com.uv.routinesappuv.R
import com.uv.routinesappuv.databinding.ActivityMainBinding
import androidx.activity.OnBackPressedDispatcherOwner

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) {
                // Cuando no hay fragments en la pila de retroceso, finaliza la actividad
                finish()
            }
        }
    }
}