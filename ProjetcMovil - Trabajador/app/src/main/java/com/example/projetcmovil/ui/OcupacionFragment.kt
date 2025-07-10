package com.example.projetcmovil.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.projetcmovil.R
import com.example.projetcmovil.data.model.Categoria
import com.example.projetcmovil.databinding.FragmentOcupacionBinding
import com.example.projetcmovil.viewModel.OcupacionViewModel


class OcupacionFragment : Fragment() {

    private lateinit var binding: FragmentOcupacionBinding
    private val viewModel: OcupacionViewModel by viewModels()
    private val selectedCategorias = mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOcupacionBinding.inflate(inflater, container, false)
        setupObservers()
        viewModel.obtenerCategorias()
        return binding.root
    }

    private fun setupObservers() {
        viewModel.categorias.observe(viewLifecycleOwner) { lista ->
            if (!lista.isNullOrEmpty()) {
                mostrarCheckBoxes(lista)
            } else {
                Toast.makeText(requireContext(), "No hay categorías disponibles", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.mensajeError.observe(viewLifecycleOwner) { mensaje ->
            Toast.makeText(requireContext(), mensaje, Toast.LENGTH_LONG).show()
        }

        binding.btnAgregarOcupacion.setOnClickListener {
            capturarSeleccionadas()
        }
    }

    private fun mostrarCheckBoxes(categorias: List<Categoria>) {
        categorias.forEach { categoria ->
            val checkBox = CheckBox(requireContext())
            checkBox.text = categoria.nombre
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedCategorias.add(categoria.id)
                } else {
                    selectedCategorias.remove(categoria.id)
                }
            }
            binding.layoutCheckboxes.addView(checkBox)
        }
    }

    private fun capturarSeleccionadas() {
        if (selectedCategorias.isEmpty()) {
            Toast.makeText(requireContext(), "Seleccione al menos una ocupación", Toast.LENGTH_SHORT).show()
            return
        }

        Toast.makeText(requireContext(), "Seleccionaste: $selectedCategorias", Toast.LENGTH_LONG).show()
        // Aquí puedes hacer un navigate con esos IDs o guardarlos en ViewModel
    }
}