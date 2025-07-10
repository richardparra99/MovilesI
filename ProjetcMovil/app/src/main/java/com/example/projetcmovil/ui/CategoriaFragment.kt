package com.example.projetcmovil.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projetcmovil.databinding.FragmentCategoriaBinding
import com.example.projetcmovil.ui.adapters.CategoriaAdapter
import com.example.projetcmovil.viewModel.CategoriaViewModel

class CategoriaFragment : Fragment() {
    private lateinit var binding: FragmentCategoriaBinding
    private val viewModel: CategoriaViewModel by viewModels()
    private lateinit var categoriaAdapter: CategoriaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriaBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setupSearchView()
        observarViewModel()
        viewModel.obtenerCategorias()
        return binding.root
    }

    private fun setupRecyclerView() {
        categoriaAdapter = CategoriaAdapter { categoria ->
            // Navegar al fragment de trabajadores pasando el id
            val action = CategoriaFragmentDirections
                .actionCategoriaFragmentToTrabajadoresFragment(categoria.id)
            findNavController().navigate(action)
        }

        binding.recyclerCategorias.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = categoriaAdapter
        }
    }

    private fun setupSearchView() {
        binding.searchCategoria.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                categoriaAdapter.filtrar(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                categoriaAdapter.filtrar(newText ?: "")
                return true
            }
        })
    }

    private fun observarViewModel() {
        viewModel.categorias.observe(viewLifecycleOwner) { lista ->
            if (!lista.isNullOrEmpty()) {
                categoriaAdapter.actualizarLista(lista)
            } else {
                Toast.makeText(requireContext(), "No hay categorías disponibles", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.mensajeError.observe(viewLifecycleOwner) { errorMsg ->
            Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_LONG).show()
        }
    }
}