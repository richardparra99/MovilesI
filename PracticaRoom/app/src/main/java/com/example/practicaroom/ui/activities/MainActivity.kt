package com.example.practicaroom.ui.activities

import RecetaAdapter
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaroom.R
import com.example.practicaroom.databinding.ActivityMainBinding
import com.example.practicaroom.db.models.Receta
import com.example.practicaroom.ui.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private val viewmodel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupRecyclerView()
        setupViewModelObservers()
        viewmodel.loadPeople(this)
    }

    override fun onResume() {
        super.onResume()
        viewmodel.loadPeople(this)
    }

    private fun setupViewModelObservers() {
        viewmodel.peopleList.observe(this){
            val adapter = binding.rvRecetaLista.adapter as RecetaAdapter
            adapter.setData(it)
        }
    }

    private fun setupRecyclerView() {
        val adapter = RecetaAdapter(arrayListOf())
        adapter.persoonClickListerner = object : RecetaAdapter.PersonClickListener{
            override fun onRecetaClick(receta: Receta) {
                /*val intent = RecetaDetalleActivity.detailIntent(this@MainActivity, receta)
                startActivity(intent)*/
            }

            override fun onPersonDetailClick(receta: Receta) {
                val intent = Intent(this@MainActivity, DetalleListaRecetaActivity::class.java)
                intent.putExtra(DetalleListaRecetaActivity.PARAM_RECETA, receta)
                startActivity(intent)
            }
        }
        binding.rvRecetaLista.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(this@MainActivity).apply {
                orientation = RecyclerView.VERTICAL
            }
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    LinearLayoutManager.VERTICAL
                )
            )
        }
        binding.btnCrearReceta.setOnClickListener {
            val intent = Intent(this, RecetaDetalleActivity::class.java)
            startActivity(intent)
        }

    }
    /*companion object {
        private const val PARAM_RECETA_GUARDADO = "receta_guardada"
        private const val PARAM_INSERTADO = "insertado"
        fun returnIntent(guardarReceta: Receta, isInserted: Boolean): Intent {
            return Intent().apply {
                putExtra(PARAM_RECETA_GUARDADO, guardarReceta)
                putExtra(PARAM_INSERTADO, isInserted)
            }
        }
    }*/

}