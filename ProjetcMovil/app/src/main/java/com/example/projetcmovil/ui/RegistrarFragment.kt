package com.example.projetcmovil.ui

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.projetcmovil.R
import com.example.projetcmovil.databinding.FragmentRegistrarBinding
import com.example.projetcmovil.viewModel.RegistrarViewModel


class RegistrarFragment : Fragment() {
    private lateinit var binding: FragmentRegistrarBinding
    private val viewModel: RegistrarViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrarBinding.inflate(inflater, container, false)
        setupEventListeners()
        return binding.root
    }

    private fun setupEventListeners() {
        binding.btnRegistrar.setOnClickListener {
            val nombre = binding.textInputNombre.editText?.text.toString().trim()
            val apellido = binding.textInputApellido.editText?.text.toString().trim()
            val correo = binding.textInputCorreos.editText?.text.toString().trim()
            val contrasena = binding.textInputContrasenas.editText?.text.toString().trim()

            if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                Toast.makeText(requireContext(), "Correo inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.registrarCliente(
                nombre,
                apellido,
                correo,
                contrasena,
                onSuccess = {
                    Toast.makeText(requireContext(), "Registro exitoso", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registrarFragment_to_LoginFragment)
                },
                onError = { errorMsg ->
                    Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_LONG).show()
                }
            )
        }
    }
}