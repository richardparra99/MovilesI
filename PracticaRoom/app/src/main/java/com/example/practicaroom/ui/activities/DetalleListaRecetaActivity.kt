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

class DetalleListaRecetaActivity : AppCompatActivity() {
    private var receta: Receta? = null
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
        receta = intent.getSerializableExtra(PARAM_RECETA) as? Receta
        mostrarDatosReceta()
    }
    private fun mostrarDatosReceta() {
        receta?.let {
            binding.txtTitle.setText(it.titulo)
            binding.txtIngredientes.setText(it.ingredientes)
            binding.txtPreparacion.setText(it.preparacion)
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