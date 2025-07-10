package com.example.projetcmovil.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projetcmovil.R
import com.example.projetcmovil.databinding.FragmentTrabajadoresBinding
import com.example.projetcmovil.ui.adapters.TrabajadorAdapter
import com.example.projetcmovil.viewModel.TrabajadoresViewModel

class TrabajadoresFragment : Fragment() {
    private lateinit var binding: FragmentTrabajadoresBinding
    private val args: TrabajadoresFragmentArgs by navArgs()
    private val viewModel: TrabajadoresViewModel by viewModels()
    private lateinit var adapter: TrabajadorAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrabajadoresBinding.inflate(inflater, container, false)
        setupRecyclerView()
        observarViewModel()
        viewModel.obtenerTrabajadores(args.categoriaId)
        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = TrabajadorAdapter { trabajador ->
            val action = TrabajadoresFragmentDirections
                .actionTrabajadoresFragmentToDetalleTrabajadorFragment(trabajador.id)
            findNavController().navigate(action)
        }
        binding.recyclerTrabajadores.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerTrabajadores.adapter = adapter
    }


    private fun observarViewModel() {
        viewModel.trabajadores.observe(viewLifecycleOwner) { lista ->
            adapter.actualizarLista(lista ?: emptyList())
        }

        viewModel.mensajeError.observe(viewLifecycleOwner) { msg ->
            Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
        }
    }
}