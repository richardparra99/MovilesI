package com.example.projetcmovil.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.projetcmovil.R
import com.example.projetcmovil.databinding.FragmentFotoPerfilBinding
import com.example.projetcmovil.viewModel.FotoPerfilViewModel

class FotoPerfilFragment : Fragment() {

    private lateinit var binding: FragmentFotoPerfilBinding
    private val viewModel: FotoPerfilViewModel by viewModels()
    private var selectedImageUri: Uri? = null
    private var trabajadorId: Int = 0

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            selectedImageUri = it
            Glide.with(this).load(it).into(binding.imgPerfil)
            Log.d("FotoPerfilFragment", "Imagen seleccionada: $it")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFotoPerfilBinding.inflate(inflater, container, false)
        trabajadorId = arguments?.getInt("trabajadorId") ?: 0
        Log.d("FotoPerfilFragment", "Trabajador ID: $trabajadorId")

        setupListeners()
        setupObservers()

        return binding.root
    }

    private fun setupListeners() {
        binding.btnSeleccionarImagen.setOnClickListener {
            pickImage.launch("image/*")
        }

        binding.btnCompletarRegistro.setOnClickListener {
            if (selectedImageUri == null) {
                Toast.makeText(requireContext(), "Seleccione una imagen", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Log.d("FotoPerfilFragment", "Subiendo foto para $trabajadorId")
            viewModel.subirFotoPerfil(selectedImageUri!!)
        }
    }

    private fun setupObservers() {
        viewModel.mensaje.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.exito.observe(viewLifecycleOwner) { ok ->
            if (ok) {
                findNavController().navigate(R.id.action_fotoPerfilFragment_to_loginTrabajoFragment)
            }
        }
    }
}
