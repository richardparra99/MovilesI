package com.example.practicaroom.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.practicaroom.databinding.ActivityRecetaDetalleBinding
import com.example.practicaroom.db.models.Receta
import com.example.practicaroom.repositories.RecetaRepository
import com.example.practicaroom.ui.viewmodels.RecetaDetalleViewModel
import kotlinx.coroutines.launch
import com.example.practicaroom.R

class RecetaDetalleActivity : AppCompatActivity() {
    private var receta: Receta? = null
    private lateinit var binding: ActivityRecetaDetalleBinding
    private val viewModel: RecetaDetalleViewModel by viewModels()

    private val listaIngredientesDisponibles = listOf("Arroz", "Huevo", "Pollo", "Ajo", "Lechuga", "Tomate", "Queso", "Fideo Largo")
    private val checkBoxes = mutableListOf<CheckBox>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecetaDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        receta = intent.getSerializableExtra(PARAM_RECETA) as? Receta

        viewModel.cargarIngredientes(this, receta, intent.getStringArrayListExtra("ingredientes_seleccionados"))
        cargarRecetaDetalle()
        setupCheckBoxes()
        setupEventListeners()

        observarIngredientes()
    }

    private fun setupCheckBoxes() {
        val container = findViewById<LinearLayout>(R.id.checkBoxContainer)
        container.removeAllViews()
        checkBoxes.clear()

        for (ingrediente in listaIngredientesDisponibles) {
            val checkBox = CheckBox(this).apply {
                text = ingrediente
                isChecked = viewModel.ingredientesSeleccionados.value?.contains(ingrediente) == true

                setOnCheckedChangeListener { _, _ ->
                    viewModel.toggleIngrediente(ingrediente)
                }
            }
            checkBoxes.add(checkBox)
            container.addView(checkBox)
        }
    }

    private fun observarIngredientes() {
        viewModel.ingredientesSeleccionados.observe(this) { seleccionados ->
            checkBoxes.forEach { cb ->
                cb.isChecked = seleccionados.contains(cb.text.toString())
            }
        }
    }

    private fun setupEventListeners() {
        binding.btnCancelar.setOnClickListener { finish() }

        binding.btnGuardarReceta.setOnClickListener {
            val titulo = binding.txtTitle.editText?.text.toString().trim()
            val preparacion = binding.txtPreparacion.editText?.text.toString().trim()
            val ingredientesSeleccionados = viewModel.ingredientesSeleccionados.value ?: emptyList()

            val isNueva = receta?.id == 0 || receta == null

            if (titulo.isEmpty() || preparacion.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (ingredientesSeleccionados.isEmpty()) {
                Toast.makeText(this, "Selecciona al menos un ingrediente", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nuevaReceta = Receta(titulo, preparacion).apply {
                id = receta?.id ?: 0
            }

            binding.btnGuardarReceta.isEnabled = false

            lifecycleScope.launch {
                RecetaRepository.guardarRecetaConIngredientes(this@RecetaDetalleActivity, nuevaReceta, ingredientesSeleccionados)
                binding.btnGuardarReceta.isEnabled = true
                finish()
            }
        }

        binding.btnEliminar.setOnClickListener {
            receta?.let {
                lifecycleScope.launch {
                    RecetaRepository.EliminarReceta(this@RecetaDetalleActivity, it)
                    finish()
                }
            }
        }
    }

    private fun cargarRecetaDetalle() {
        receta?.let {
            binding.txtTitle.editText?.setText(it.titulo)
            binding.txtPreparacion.editText?.setText(it.preparacion)
        }
    }


    companion object {
        const val PARAM_RECETA = "receta"
        fun detailIntent(context: Context, receta: Receta): Intent {
            return Intent(context, RecetaDetalleActivity::class.java).apply {
                putExtra(PARAM_RECETA, receta)
            }
        }
    }
}
