package com.uv.routinesappuv.view.fragment
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.uv.routinesappuv.databinding.FragmentHomeRoutineBinding
import com.uv.routinesappuv.viewmodel.RoutinesViewModel

class HomeRoutineFragment : Fragment() {
    private lateinit var binding: FragmentHomeRoutineBinding
    private val routinesViewModel: RoutinesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeRoutineBinding.inflate(inflater)
        binding.lifecycleOwner = this
       // controladores()
        observadorViewModel();
        // Set click listener for the button


        return binding.root
    }


    private fun observadorViewModel() {
        observerListCitas()

    }

    private fun observerListCitas() {
        routinesViewModel.fetchRutinas()
        routinesViewModel.rutinas.observe(viewLifecycleOwner) { listCitas ->
            Log.d("lista:", "Listroutines $listCitas" )
            //val recycler = binding.recyclerview
           // val layoutManager = LinearLayoutManager(context)
          //  recycler.layoutManager = layoutManager
          //  val adapter = CitasAdapter(listCitas, findNavController())
           // recycler.adapter = adapter
           // adapter.notifyDataSetChanged()
        }
    }


    }


