package com.uv.routinesappuv.view.fragment
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.uv.routinesappuv.databinding.FragmentHomeRoutineBinding
import com.uv.routinesappuv.viewmodel.RoutinesViewModel
import com.uv.routinesappuv.R
import com.uv.routinesappuv.view.adapter.RoutinesAdapter

class HomeRoutineFragment : Fragment() {
    private lateinit var binding: FragmentHomeRoutineBinding
    private val routinesViewModel: RoutinesViewModel by viewModels()
    private lateinit var auth: FirebaseAuth
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controladores();
        observadorViewModel();
        capturarData()
        getEmail()
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Mueve la tarea al fondo
                    requireActivity().moveTaskToBack(true)
                }
            }
        )
    }
    private fun controladores() {
        binding.btnFragmentNuevaCita.setOnClickListener {
            findNavController().navigate(R.id.fragment_add_routine)
        }
        //temporal mientras se hace lo de detalle rutina para poder ver los fragments
        binding.btnSubmit2.setOnClickListener {
            findNavController().navigate(R.id.fragment_edit_routine)
        }
    }
    private fun capturarData(){
        val email = arguments?.getString("email")
        Log.d("email logged in", "${email}")
    }
    private fun observadorViewModel() {
        observerListRutinas()

    }
    private fun getEmail(){
        auth = FirebaseAuth.getInstance()

        // Obtener el usuario actualmente autenticado
        val user = auth.currentUser
        user?.let {
            // Obtener el correo electrónico del usuario
            val email = user.email
           Log.d("emaillll", "${email}")
        } ?: run {
            // Manejar el caso en que no hay un usuario autenticado
          //  emailTextView.text = "No hay usuario autenticado"
        }
    }

    private fun observerListRutinas() {
        routinesViewModel.fetchRutinas()
        routinesViewModel.rutinas.observe(viewLifecycleOwner) { listRutinas ->
            val recycler = binding.recyclerview
            val layoutManager = LinearLayoutManager(context)
            recycler.layoutManager = layoutManager
            val adapter = RoutinesAdapter(listRutinas, findNavController())
            recycler.adapter = adapter
            adapter.notifyDataSetChanged()
        }

        routinesViewModel.rutinas.observe(viewLifecycleOwner, Observer { rutinas ->
            // Log each rutina or update your UI here
            rutinas.forEach { rutina ->
                Log.d("HomeRoutineFragment", "Rutina: $rutina")
            }
        })
    }


    }


