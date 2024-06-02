package com.uv.routinesappuv.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uv.routinesappuv.R
import com.uv.routinesappuv.databinding.FragmentDetailRoutineBinding

class DetailRoutineFragment : Fragment() {
    private lateinit var binding: FragmentDetailRoutineBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailRoutineBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }
}