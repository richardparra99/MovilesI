package com.example.projetcmovil.ui

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.projetcmovil.R
import com.example.projetcmovil.databinding.FragmentLoginBinding
import com.example.projetcmovil.viewModel.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        setupEventListeners()


        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        return binding.root
    }

    private fun setupEventListeners() {
        binding.btnAcceder.setOnClickListener {
            val correo = binding.textInputCorreo.editText?.text.toString().trim()
            val contrasena = binding.textInputContrasena.editText?.text.toString().trim()

            if (correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                Toast.makeText(requireContext(), "Correo inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.login(
                correo,
                contrasena,
                onSuccess = {
                    Toast.makeText(requireContext(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_LoginFragment_to_categoriaFragment)
                },
                onError = { errorMsg ->
                    Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_LONG).show()
                }
            )
        }
    }
}
