package com.example.practicaroom.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.practicaroom.R
import com.example.practicaroom.databinding.ActivityRecetaDetalleBinding
import com.example.practicaroom.db.models.Receta
import com.example.practicaroom.repositories.RecetaRepository
import com.example.practicaroom.ui.viewmodels.RecetaDetalleViewModel
import kotlinx.coroutines.launch

class RecetaDetalleActivity : AppCompatActivity() {
    private var receta: Receta? = null
    private lateinit var binding: ActivityRecetaDetalleBinding
    private val viewModel: RecetaDetalleViewModel by viewModels()

    private val listaIngredientesDisponibles = listOf("Arroz", "Huevo", "Pollo", "Ajo", "Lechuga", "Tomate", "Queso", "Fideo Largo")
    private val checkBoxes = mutableListOf<CheckBox>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRecetaDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        receta = intent.getSerializableExtra(PARAM_RECETA) as Receta?
        val ingredientesIniciales = intent.getStringArrayListExtra("ingredientes_seleccionados")
        ingredientesIniciales?.let {
            viewModel.setIngredientesIniciales(it)
        }
        cargarRecetaDetalle(receta)
        recuperIngredienteSelecionado()
        setupEventListeners()
        setupCheckBoxes()
    }

    private fun setupCheckBoxes() {
        val container = findViewById<LinearLayout>(R.id.checkBoxContainer)
        container.removeAllViews()
        checkBoxes.clear()

        for (ingrediente in listaIngredientesDisponibles) {
            val checkBox = CheckBox(this).apply {
                text = ingrediente
                isChecked = viewModel.ingredientesSeleccionados.value?.contains(ingrediente) == true

                setOnCheckedChangeListener { _, isChecked ->
                    viewModel.toggleIngrediente(ingrediente)
                }
            }
            checkBoxes.add(checkBox)
            container.addView(checkBox)
        }

    }

    private fun recuperIngredienteSelecionado() {
        val ingredientesRecibidos = intent.getStringArrayListExtra("ingredientes_seleccionados")
        ingredientesRecibidos?.let {
            binding.txtIngredientes.editText?.setText(it.joinToString(","))
        }
    }


    private fun setupEventListeners() {
        binding.btnCancelar.setOnClickListener { finish() }
        binding.btnGuardarReceta.setOnClickListener { guardarReceta() }
        binding.btnEliminar.setOnClickListener { eliminarReceta() }
    }

    private fun eliminarReceta() {
        lifecycleScope.launch {
            receta?.let {
                RecetaRepository.EliminarReceta(
                    context = this@RecetaDetalleActivity,
                    receta = it
                )
            }
            finish()
        }
    }

    private fun guardarReceta() {
        val nuevaReceta = Receta(
            binding.txtTitle.editText?.text.toString(),
            binding.txtPreparacion.editText?.text.toString()
        )

        val ingredientes = viewModel.ingredientesSeleccionados.value?.toList() ?: emptyList()

        lifecycleScope.launch {
            val recetaGuardada = RecetaRepository.guardarRecetaConIngredientes(
                context = this@RecetaDetalleActivity,
                receta = nuevaReceta,
                nombresIngredientes = ingredientes
            )

            // Debug opcional
            // Log.d("DEBUG", "Receta guardada con ID: ${recetaGuardada.id}")

            finish()
        }
    }

    private fun cargarRecetaDetalle(receta: Receta?) {
        if (receta == null || receta.id == 0) {
            binding.btnEliminar.isEnabled = false
            binding.btnEliminar.isClickable = false
            return
        }
        binding.txtTitle.editText?.setText(receta.titulo)
        binding.txtPreparacion.editText?.setText(receta.preparacion)
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