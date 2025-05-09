package com.example.practicaroom.ui.activities


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practicaroom.R
import com.example.practicaroom.databinding.ActivityBuscarRecetaBinding
import com.example.practicaroom.ui.viewmodels.BuscarRecetaViewModel

class BuscarRecetaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBuscarRecetaBinding
    private val viewModel: BuscarRecetaViewModel by viewModels()
    private val ingredientesSeleccionados = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBuscarRecetaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupBotonesIngredientes()
        setupBotonBuscar()
        observarResultadoBusqueda()
    }

    private fun setupBotonBuscar() {
        binding.btnBuscarReceta.setOnClickListener {
            if (ingredientesSeleccionados.isEmpty()) {
                Toast.makeText(this, "Selecciona al menos un ingrediente", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Aquí ya usamos viewModelScope
            viewModel.buscarRecetas(this, ingredientesSeleccionados)
        }
    }

    private fun observarResultadoBusqueda() {
        viewModel.recetasFiltradas.observe(this) { recetas ->
            if (recetas.isNullOrEmpty()) {
                Toast.makeText(this, "No se encontraron recetas. Puedes crear una nueva.", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java)
                intent.putStringArrayListExtra("ingredientes_seleccionados", ArrayList(ingredientesSeleccionados))
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, ListaRecetaFiltradasActivity::class.java)
                intent.putExtra("recetas_encontradas", ArrayList(recetas))
                startActivity(intent)
            }
        }
    }

    private fun setupBotonesIngredientes() {
        val botonesIngredientes = listOf(
            binding.btnArroz,
            binding.btnHuevo,
            binding.btnPollo,
            binding.btnAjo,
            binding.btnLechuga,
            binding.btnTomate,
            binding.btnQueso,
            binding.btnFideoLargo
        )

        botonesIngredientes.forEach { boton ->
            boton.setOnClickListener {
                toggleSeleccionIngrediente(boton)
            }
        }
    }

    private fun toggleSeleccionIngrediente(boton: Button) {
        val ingrediente = boton.text.toString()

        if (ingredientesSeleccionados.contains(ingrediente)) {
            ingredientesSeleccionados.remove(ingrediente)
            boton.setBackgroundColor(resources.getColor(android.R.color.darker_gray, theme))
        } else {
            ingredientesSeleccionados.add(ingrediente)
            boton.setBackgroundColor(resources.getColor(android.R.color.holo_blue_light, theme))
        }
    }
}