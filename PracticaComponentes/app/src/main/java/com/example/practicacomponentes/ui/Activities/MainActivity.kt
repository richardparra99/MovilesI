package com.example.practicacomponentes.ui.Activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practicacomponentes.R
import com.example.practicacomponentes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
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
        setupEventListener()
    }

    private fun setupEventListener() {
        binding.numberPicker1.setOnValueChangeListener {
            Toast.makeText(this, "Valor cambiado picker1: $it", Toast.LENGTH_SHORT).show()
        }
        binding.numberPicker2.setOnValueChangeListener {
            Toast.makeText(this, "Valor cambiado picker2: $it", Toast.LENGTH_SHORT).show()
        }
        binding.numberPicker2.setSelectedValue(5)
    }
}