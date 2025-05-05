package com.example.practicaroom.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practicaroom.R
import com.example.practicaroom.databinding.ActivityDetalleListaRecetaBinding
import com.example.practicaroom.ui.viewmodels.DetalleListaRecetaViewModel

class DetalleListaRecetaActivity : AppCompatActivity() {
    private var recetaId: Int = -1
    private lateinit var binding: ActivityDetalleListaRecetaBinding
    private val viewModel: DetalleListaRecetaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetalleListaRecetaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recibirValidarReceta()
        observarReceta()
    }

    private fun recibirValidarReceta() {
        recetaId = intent.getIntExtra(PARAM_RECETA_ID, -1)
        if (recetaId != -1) {
            viewModel.cargarReceta(this, recetaId)
        } else {
            Toast.makeText(this, "Receta no válida", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun observarReceta() {
        viewModel.recetaConIngredientes.observe(this) { recetaConIngredientes ->
            if (recetaConIngredientes != null) {
                binding.txtTitle.setText(recetaConIngredientes.receta.titulo)
                binding.txtPreparacion.setText(recetaConIngredientes.receta.preparacion)
                binding.txtIngredientes.setText(
                    recetaConIngredientes.ingrediente.joinToString(", ") { it.nombre }
                )
            } else {
                Toast.makeText(this, "Receta no encontrada", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    companion object {
        const val PARAM_RECETA_ID = "receta_id"

        fun detailIntent(context: Context, recetaId: Int): Intent {
            return Intent(context, DetalleListaRecetaActivity::class.java).apply {
                putExtra(PARAM_RECETA_ID, recetaId)
            }
        }
    }
}