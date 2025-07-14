package com.example.projetcmovil.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projetcmovil.databinding.FragmentMisCitasBinding
import com.example.projetcmovil.data.repository.Repositorio
import com.example.projetcmovil.ui.adapters.CitaAdapter
import com.example.projetcmovil.viewModel.MisCitasViewModel

class MisCitasFragment : Fragment() {
    private lateinit var binding: FragmentMisCitasBinding
    private val viewModel: MisCitasViewModel by viewModels()
    private lateinit var repository: Repositorio

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMisCitasBinding.inflate(inflater, container, false)
        repository = Repositorio(requireContext())

        binding.recyclerCitas.layoutManager = LinearLayoutManager(requireContext())

        val workerId = repository.obtenerWorkerId()
        viewModel.obtenerCitas(workerId)

        setupObservers()
        return binding.root
    }

    private fun setupObservers() {
        viewModel.citas.observe(viewLifecycleOwner) { citas ->
            binding.recyclerCitas.adapter = CitaAdapter(citas)
        }

        viewModel.mensajeError.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }
    }
}
