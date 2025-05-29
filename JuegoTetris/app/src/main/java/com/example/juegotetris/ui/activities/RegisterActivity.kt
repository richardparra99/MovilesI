package com.example.juegotetris.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.juegotetris.R
import com.example.juegotetris.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupEventListeners()
    }

    private fun setupEventListeners() {
        binding.btnPlay.setOnClickListener {
            val name = binding.TextName.editText?.text.toString().trim()

            if (name.isNotEmpty()) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("playerName", name)
                startActivity(intent)
                finish()
            } else {
                binding.TextName.error = "Por favor ingrese un nombre"
            }
        }
    }
}