package com.uv.routinesappuv.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.uv.routinesappuv.R
import com.uv.routinesappuv.databinding.FragmentEditRoutineBinding
import com.uv.routinesappuv.model.Rutina
import com.uv.routinesappuv.view.adapter.ExercisesAdapter
import com.uv.routinesappuv.viewmodel.RoutinesViewModel

class EditRoutineFragment : Fragment() {
    private lateinit var binding: FragmentEditRoutineBinding

    private val rutinasViewModel: RoutinesViewModel by viewModels()
    private lateinit var receivedRutina: Rutina

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditRoutineBinding.inflate(inflater, container, false)
        setupListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        dataRutina()
        controladores()
    }

    private fun controladores() {

        binding.btnNuevoEjercicio.setOnClickListener{
            val bundle = Bundle()
            bundle.putSerializable("rutina", receivedRutina) // Pasar toda la rutina
            findNavController().navigate(
                R.id.action_fragment_edit_routine_to_fragment_edit_exercise,
                bundle
            )
        }
        binding.contentToolbarEditar.toolbarEdit.setNavigationOnClickListener { onBackPressed() }
    }

    private fun onBackPressed() {
        findNavController().navigateUp()
    }

    private fun dataRutina() {
        val receivedBundle = arguments
        receivedRutina = receivedBundle?.getSerializable("clave") as Rutina

        binding.etNombreRutina.setText(receivedRutina.nombre_rutina)
        binding.etDescripcion.setText(receivedRutina.descripcion_rutina)

        val recycler = binding.recyclerview
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = ExercisesAdapter(receivedRutina.ejercicios)
    }

    private fun editRutina() {
        val updatedRutina = receivedRutina.copy(
            nombre_rutina = binding.etNombreRutina.text.toString(),
            descripcion_rutina = binding.etDescripcion.text.toString()
        )

        rutinasViewModel.updateRutina(updatedRutina)
        findNavController().navigate(
            R.id.action_fragment_edit_routine_to_fragment_home_routine
        )
    }

    private fun setupListeners() {
        val textWatchers = listOf(
            binding.etDescripcion,
            binding.etNombreRutina
        )

        for (textView in textWatchers) {
            textView.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    validateFields()
                }
                override fun afterTextChanged(s: Editable?) {}
            })
        }

        binding.btnEdit.setOnClickListener {
            if (binding.etNombreRutina.text.toString().isEmpty() ||
                binding.etDescripcion.text.toString().isEmpty()) {

                //binding.btnEdit.setBackgroundColor(resources.getColor(R.color.gray))
            } else {
                editRutina()
            }
        }
    }

    private fun validateFields() {
        val isDescripcionFilled = binding.etDescripcion.text.toString().isNotEmpty()
        val isNombreRutinaFilled = binding.etNombreRutina.text.toString().isNotEmpty()

        binding.btnEdit.isEnabled = isDescripcionFilled && isNombreRutinaFilled

        if (binding.btnEdit.isEnabled) {
            binding.btnEdit.setBackgroundColor(resources.getColor(R.color.enabled_button_color))
        } else {
            binding.btnEdit.setBackgroundColor(resources.getColor(R.color.disabled_button_color))
            Toast.makeText(requireContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
        }
    }
}
