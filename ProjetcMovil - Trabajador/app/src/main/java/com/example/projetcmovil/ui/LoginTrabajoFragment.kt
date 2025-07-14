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
import com.example.projetcmovil.databinding.FragmentLoginTrabajoBinding
import com.example.projetcmovil.viewModel.LoginTrabajoViewModel

class LoginTrabajoFragment : Fragment() {
    private lateinit var binding: FragmentLoginTrabajoBinding
    private val viewModel: LoginTrabajoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginTrabajoBinding.inflate(inflater, container, false)
        setupListeners()
        return binding.root
    }

    private fun setupListeners() {
        binding.btnAcceder.setOnClickListener {
            val correo = binding.textInputCorreo.editText?.text.toString().trim()
            val contrasena = binding.textInputContrasena.editText?.text.toString().trim()

            if (correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                Toast.makeText(requireContext(), "Correo inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.login(
                correo,
                contrasena,
                onSuccess = { token ->
                    Toast.makeText(requireContext(), "Bienvenido!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_loginTrabajoFragment_to_misCitasFragment)
                },
                onError = { mensaje ->
                    Toast.makeText(requireContext(), mensaje, Toast.LENGTH_LONG).show()
                }
            )
        }
    }
}