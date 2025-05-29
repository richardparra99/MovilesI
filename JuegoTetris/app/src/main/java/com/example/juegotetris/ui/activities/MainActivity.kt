package com.example.juegotetris.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.juegotetris.R
import com.example.juegotetris.databinding.ActivityMainBinding
import com.example.juegotetris.ui.viewModels.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

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
        setupEventListerns()
    }

    private fun setupEventListerns() {
        binding.btnDown.setOnClickListener {
            binding.tetrisBoardView.dropToBottom()
        }
        binding.btnRotate.setOnClickListener {
            binding.tetrisBoardView.rotateCurrentPiece()
        }
        binding.tetrisBoardView.updateScoreCallback { score, level ->
            binding.scoreText.text = "Puntos: $score"
            binding.levelText.text = "Nivel: $level"
        }
        binding.tetrisBoardView.onGameOver {
            runOnUiThread {
                val playerName = intent.getStringExtra("playerName") ?: "Jugador"
                val finalScore = binding.tetrisBoardView.getFinalScore()

                viewModel.guardarPuntaje(this, playerName, finalScore)


                AlertDialog.Builder(this)
                    .setTitle("¡Game Over!")
                    .setMessage("¿Querés reiniciar la partida?")
                    .setPositiveButton("Sí") { _, _ ->
                        restartGame()
                    }
                    .setNegativeButton("No") { _, _ ->
                        val intent = Intent(this, RegisterActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    .setCancelable(false)
                    .show()
            }
        }
    }

    private fun restartGame() {
        binding.tetrisBoardView.resetGame()
    }
}