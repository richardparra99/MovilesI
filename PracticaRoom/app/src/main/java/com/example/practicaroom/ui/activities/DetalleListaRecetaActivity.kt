package com.example.practicaroom.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practicaroom.R
import com.example.practicaroom.databinding.ActivityDetalleListaRecetaBinding
import com.example.practicaroom.db.models.Receta
import com.example.practicaroom.db.models.RecetaConIngrediente

class DetalleListaRecetaActivity : AppCompatActivity() {
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
        recetaConIngrediente = intent.getSerializableExtra(PARAM_RECETA) as? RecetaConIngrediente
        mostrarDatosReceta()
    }
    private fun mostrarDatosReceta() {
        recetaConIngrediente?.let {
            binding.txtTitle.setText(it.receta.titulo)
            binding.txtPreparacion.setText(it.receta.preparacion)
            binding.txtIngredientes.setText(it.ingrediente.joinToString(", ") { i -> i.nombre })
            }
        }

    companion object {
        const val PARAM_RECETA = "receta"
        fun detailIntent(context: Context, receta: Receta): Intent? {
            val intent = Intent(context, RecetaDetalleActivity::class.java)
            intent.putExtra(PARAM_RECETA, receta)
            return intent
        }
    }
}