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
import com.bumptech.glide.Glide
import com.example.projetcmovil.databinding.FragmentDetalleTrabajadorBinding
import com.example.projetcmovil.viewModel.DetalleTrabajadorViewModel

class DetalleTrabajadorFragment : Fragment() {
    private val args: DetalleTrabajadorFragmentArgs by navArgs()
    private val viewModel: DetalleTrabajadorViewModel by viewModels()
    private lateinit var binding: FragmentDetalleTrabajadorBinding

    private var nombreCompleto = ""
    private var imagenUrl = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetalleTrabajadorBinding.inflate(inflater, container, false)
        viewModel.obtenerDetalleTrabajador(args.trabajadorId)
        observarViewModel()
        setupBotonContactar()
        return binding.root
    }

    private fun observarViewModel() {
        viewModel.trabajador.observe(viewLifecycleOwner) { trabajador ->
            nombreCompleto = "${trabajador.user?.profile?.name} ${trabajador.user?.profile?.last_name}"
            imagenUrl = trabajador.picture_url ?: ""

            binding.tvNombre.text = nombreCompleto
            binding.tvRating.text = "${trabajador.average_rating}% calificación"
            binding.tvTrabajos.text = "${trabajador.reviews_count} trabajos completados"
            binding.tvOficios.text = "Plomero, electricista, pintor"
            Glide.with(this)
                .load(imagenUrl)
                .into(binding.imgPerfil)
        }

        viewModel.mensajeError.observe(viewLifecycleOwner) { mensaje ->
            Toast.makeText(requireContext(), mensaje, Toast.LENGTH_LONG).show()
        }
    }

    private fun setupBotonContactar() {
        binding.btnContactar.setOnClickListener {
            val action = DetalleTrabajadorFragmentDirections
                .actionDetalleTrabajadorFragmentToChatFragment(
                    trabajadorId = args.trabajadorId,
                    nombreTrabajador = nombreCompleto,
                    imagenUrl = imagenUrl
                )
            findNavController().navigate(action)
        }
    }
}