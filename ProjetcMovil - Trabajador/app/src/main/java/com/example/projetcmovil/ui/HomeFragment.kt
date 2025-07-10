package com.example.projetcmovil.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.projetcmovil.R
import com.example.projetcmovil.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupEventListener()
        return binding.root
    }

    private fun setupEventListener() {
        binding.btnIniciar.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_loginTrabajoFragment)
        }
        binding.btnRegistro.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_registroTrabajoFragment)
        }
    }
}