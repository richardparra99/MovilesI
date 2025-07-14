package com.example.projetcmovil.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.projetcmovil.R
import com.example.projetcmovil.data.model.Categoria
import com.example.projetcmovil.data.model.OnlyId
import com.example.projetcmovil.databinding.FragmentOcupacionBinding
import com.example.projetcmovil.viewModel.OcupacionViewModel


class OcupacionFragment : Fragment() {

    private lateinit var binding: FragmentOcupacionBinding
    private val viewModel: OcupacionViewModel by viewModels()
    private val selectedCategorias = mutableSetOf<OnlyId>()
    private var usuarioId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOcupacionBinding.inflate(inflater, container, false)
        usuarioId = arguments?.getInt("trabajadorId") ?: 0
        Log.d("OcupacionFragment", "Usuario ID recibido: $usuarioId")
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
            if (selectedCategorias.isEmpty()) {
                Toast.makeText(requireContext(), "Seleccione al menos una ocupación", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("OcupacionFragment", "Enviando ocupaciones para userId $usuarioId: $selectedCategorias")
                viewModel.agregarOcupaciones(usuarioId, selectedCategorias.toList(),
                    onSuccess = {
                        Toast.makeText(requireContext(), "Ocupaciones guardadas", Toast.LENGTH_SHORT).show()
                        val bundle = Bundle().apply {
                            putInt("trabajadorId", usuarioId)
                        }
                        findNavController().navigate(R.id.action_ocupacionFragment_to_fotoPerfilFragment, bundle)
                    },
                    onError = { mensaje ->
                        Log.d("OcupacionFragment", "Error al guardar ocupaciones: $mensaje")
                        Toast.makeText(requireContext(), mensaje, Toast.LENGTH_LONG).show()
                    })
            }
        }
    }

    private fun mostrarCheckBoxes(categorias: List<Categoria>) {
        binding.layoutCheckboxes.removeAllViews() // limpia por si se recarga
        categorias.forEach { categoria ->
            val checkBox = CheckBox(requireContext())
            checkBox.text = categoria.nombre
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedCategorias.add(OnlyId(categoria.id))
                } else {
                    selectedCategorias.remove(OnlyId(categoria.id))
                }
                Log.d("OcupacionFragment", "Seleccionadas: $selectedCategorias")
            }
            binding.layoutCheckboxes.addView(checkBox)
        }
    }
}