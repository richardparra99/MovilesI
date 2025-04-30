package com.example.practicaroom.ui.activities

import RecetaAdapter
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicaroom.R
import com.example.practicaroom.databinding.ActivityListaRecetaFiltradasBinding
import com.example.practicaroom.db.models.Receta

class ListaRecetaFiltradasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListaRecetaFiltradasBinding
    private lateinit var adapter: RecetaAdapter
    private var recetasFiltradas: ArrayList<Receta> = arrayListOf()
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

        obtenerRecetasDesdeIntent()
        setupEventRecyclerView()
    }

    private fun setupEventRecyclerView() {
        adapter = RecetaAdapter(recetasFiltradas)
        binding.rvRecetasFiltradas.apply {
            layoutManager = LinearLayoutManager(this@ListaRecetaFiltradasActivity)
            adapter = this@ListaRecetaFiltradasActivity.adapter
        }
    }

    private fun obtenerRecetasDesdeIntent() {
        recetasFiltradas = intent.getSerializableExtra("recetas_encontradas") as? ArrayList<Receta> ?: arrayListOf()
    }

}