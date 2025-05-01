package com.example.practicaroom.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.practicaroom.R
import com.example.practicaroom.databinding.ActivityDetalleListaRecetaBinding
import com.example.practicaroom.db.AppDataBase
import com.example.practicaroom.db.models.Receta
import com.example.practicaroom.db.models.RecetaConIngrediente
import kotlinx.coroutines.launch

class DetalleListaRecetaActivity : AppCompatActivity() {
    private var recetaId: Int = -1
    private var recetaConIngrediente: RecetaConIngrediente? = null
    private lateinit var binding: ActivityDetalleListaRecetaBinding
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
        recetaConIngrediente = intent.getSerializableExtra(PARAM_RECETA_ID) as? RecetaConIngrediente
        recibirValidarReceta()
    }

    private fun recibirValidarReceta() {
        recetaId = intent.getIntExtra(PARAM_RECETA_ID, -1)
        if (recetaId != -1) {
            cargarDatosReceta()
        } else {
            Toast.makeText(this, "Receta no válida", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun cargarDatosReceta() {
        val db = AppDataBase.getInstance(this)

        lifecycleScope.launch {
            val recetaConIngredientes: RecetaConIngrediente? =
                db.recetaDao().obtenerRecetaConIngredientesPorId(recetaId)

            recetaConIngredientes?.let {
                binding.txtTitle.setText(it.receta.titulo)
                binding.txtPreparacion.setText(it.receta.preparacion)
                binding.txtIngredientes.setText(it.ingrediente.joinToString(", ") { i -> i.nombre })
            } ?: run {
                Toast.makeText(this@DetalleListaRecetaActivity, "Receta no encontrada", Toast.LENGTH_SHORT).show()
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