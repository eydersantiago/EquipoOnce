package com.uv.routinesappuv.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uv.routinesappuv.R
import com.uv.routinesappuv.databinding.FragmentLoginRoutineBinding
import androidx.navigation.fragment.findNavController
class LoginRoutineFragment : Fragment() {
    private lateinit var binding: FragmentLoginRoutineBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentLoginRoutineBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controladores();

    }
    private fun controladores() {
        /*binding.btnSubmit.setOnClickListener {
            findNavController().navigate(R.id.fragment_home_routine)
        }
        binding.btnSubmit2.setOnClickListener {
            findNavController().navigate(R.id.fragment_edit_routine)
        }*/
    }
}

