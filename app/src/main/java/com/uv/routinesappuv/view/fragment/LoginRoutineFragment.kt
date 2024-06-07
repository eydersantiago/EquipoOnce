package com.uv.routinesappuv.view.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.uv.routinesappuv.R
import com.uv.routinesappuv.databinding.FragmentLoginRoutineBinding
import com.uv.routinesappuv.viewmodel.LoginViewModel

class LoginRoutineFragment : Fragment() {
    private lateinit var binding: FragmentLoginRoutineBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = FragmentLoginRoutineBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences("shared", Context.MODE_PRIVATE)
        sesion()
        setup()
    }

    private fun setup() {
        binding.tvRegister.setOnClickListener {
            registerUser()
        }

        binding.btnLogin.setOnClickListener {
            loginUser()
        }


    }

    private fun registerUser() {
        val email = binding.etEmail.text.toString()
        val pass = binding.etPass.text.toString()
        loginViewModel.registerUser(email, pass) { isRegister ->
            if (isRegister) {
                goToHome(email)
            } else {
                Toast.makeText(requireContext(), "Error en el registro, vuelva a intentarlo", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun goToHome(dataEmail: String) {


            val bundle = Bundle()
            bundle.putString("mail", dataEmail)
        Log.d("email log", "${dataEmail}")
            findNavController().navigate(R.id.fragment_home_routine, bundle)


    }

    private fun loginUser() {
        val email = binding.etEmail.text.toString()
        val pass = binding.etPass.text.toString()
        loginViewModel.loginUser(email, pass) { isLogin ->
            if (isLogin) {
                goToHome(email)
            } else {
                Toast.makeText(requireContext(), "Login incorrecto, vuelva a intentarlo", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun sesion() {
        val mail = binding.etEmail.text.toString()

        val email = sharedPreferences.getString("email", "jheison@hotmail.com")
        loginViewModel.sesion(email) { isEnableView ->
            if (isEnableView) {
                binding.clContenedor.visibility = View.INVISIBLE
                goToHome(mail)
            }
        }
    }

}