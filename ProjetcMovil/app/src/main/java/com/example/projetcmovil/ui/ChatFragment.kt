package com.example.projetcmovil.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projetcmovil.databinding.FragmentChatBinding
import com.example.projetcmovil.ui.adapters.ChatAdapter
import com.example.projetcmovil.viewModel.ChatViewModel

class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding
    private val args: ChatFragmentArgs by navArgs()
    private val viewModel: ChatViewModel by viewModels()
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        setupRecyclerView()
        observarViewModel()
        viewModel.cargarMensajes(args.trabajadorId)
        binding.tvNombreTrabajador.text = args.nombreTrabajador

        binding.btnEnviar.setOnClickListener {
            val mensaje = binding.etMensaje.text.toString()
            if (mensaje.isNotEmpty()) {
                viewModel.enviarMensaje(mensaje)
                binding.etMensaje.text.clear()
            }
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter(emptyList())
        binding.recyclerChat.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = chatAdapter
        }
    }

    private fun observarViewModel() {
        viewModel.mensajes.observe(viewLifecycleOwner) { lista ->
            chatAdapter.actualizarMensajes(lista)
            binding.recyclerChat.scrollToPosition(lista.size - 1)
        }
    }
}