package com.example.practicaroom.ui.activities

import RecetaAdapter
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicaroom.R
import com.example.practicaroom.databinding.ActivityListaRecetaFiltradasBinding
import com.example.practicaroom.db.models.Receta
import com.example.practicaroom.ui.viewmodels.ListaRecetaFiltradasViewModel

class ListaRecetaFiltradasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListaRecetaFiltradasBinding
    private lateinit var adapter: RecetaAdapter
    private val viewModel: ListaRecetaFiltradasViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityListaRecetaFiltradasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
        observarRecetas()
        viewModel.cargarRecetasDesdeBundle(intent.extras)
    }

    private fun setupRecyclerView() {
        adapter = RecetaAdapter(arrayListOf())
        adapter.persoonClickListerner = object : RecetaAdapter.PersonClickListener {
            override fun onRecetaClick(receta: Receta) {}

            override fun onPersonDetailClick(receta: Receta) {
                val intent = DetalleListaRecetaActivity.detailIntent(this@ListaRecetaFiltradasActivity, receta.id)
                startActivity(intent)
            }
        }

        binding.rvRecetasFiltradas.layoutManager = LinearLayoutManager(this)
        binding.rvRecetasFiltradas.adapter = adapter
    }

    private fun observarRecetas() {
        viewModel.recetasFiltradas.observe(this) { lista ->
            if (lista.isNullOrEmpty()) {
                Toast.makeText(this, "No se pudieron recuperar las recetas.", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                adapter.setData(ArrayList(lista))
            }
        }
    }

}