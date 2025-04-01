package com.example.practicacalculadora.ui.Activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practicacalculadora.OperationType
import com.example.practicacalculadora.R
import com.example.practicacalculadora.databinding.ActivityMainBinding
import com.example.practicacalculadora.ui.viewModels.MainViewModel

class MainActivity : AppCompatActivity() {
    /*private lateinit var btn1: Button
    private lateinit var btn2: Button
    private lateinit var btn3: Button
    private lateinit var btn4: Button
    private lateinit var btn5: Button
    private lateinit var btn6: Button
    private lateinit var btn7: Button
    private lateinit var btn8: Button
    private lateinit var btn9: Button
    private lateinit var btn0: Button
    private lateinit var btnAddition: Button
    private lateinit var btnSubtraction: Button
    private lateinit var btnMultiplication: Button
    private lateinit var btnDivision: Button
    private lateinit var btnEquals: Button
    private lateinit var btnClearOne: Button
    private lateinit var btnClearEverything: Button
    private lateinit var lblResult: TextView
    private lateinit var btnMAddition: Button
    private lateinit var btnMSubtraction: Button
    private lateinit var btnMC: Button
    private lateinit var btnMR: Button*/
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupEventListeners()
        setupViewModelsObserver()
    }

    private fun setupViewModelsObserver() {
        viewModel.result.observe(this){ result ->
            if (result.isEmpty()) {
                binding.lblResult.text = "0"
            } else {
                binding.lblResult.text = result
            }
        }
        viewModel.show_error.observe(this) {
            if (it){
                Toast.makeText(this, "Error, división por 0", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupEventListeners() {
        binding.btn1.setOnClickListener { viewModel.addNumber(1) }
        binding.btn2.setOnClickListener { viewModel.addNumber(2) }
        binding.btn3.setOnClickListener { viewModel.addNumber(3) }
        binding.btn4.setOnClickListener { viewModel.addNumber(4) }
        binding.btn5.setOnClickListener { viewModel.addNumber(5) }
        binding.btn6.setOnClickListener { viewModel.addNumber(6) }
        binding.btn7.setOnClickListener { viewModel.addNumber(7) }
        binding.btn8.setOnClickListener { viewModel.addNumber(8) }
        binding.btn9.setOnClickListener { viewModel.addNumber(9) }
        binding.btn0.setOnClickListener { viewModel.addNumber(0) }
        binding.btnAddition.setOnClickListener { viewModel.startOperation(OperationType.ADDITION) }
        binding.btnSubtraction.setOnClickListener { viewModel.startOperation(OperationType.SUBTRACTION) }
        binding.btnMultiply.setOnClickListener { viewModel.startOperation(OperationType.MULTIPLICATION) }
        binding.btnDivide.setOnClickListener { viewModel.startOperation(OperationType.DIVISION) }
        binding.btnEquals.setOnClickListener { viewModel.calculateResult() }
        binding.btnClearOne.setOnClickListener { viewModel.clearOne() }
        binding.btnClearEverything.setOnClickListener { viewModel.clearEverything() }
        binding.btnMC.setOnClickListener { viewModel.limpiarMemoria() }
        binding.btnMR.setOnClickListener { viewModel.extrarMemoria() }
        binding.btnMAddition.setOnClickListener { viewModel.sumarMemoria() }
        binding.btnMSubtraction.setOnClickListener { viewModel.restarMemoria() }

    }


    /*private fun reloadScreen() {
        if (result.isEmpty()) {
            binding.lblResult.text = "0"
        } else {
            binding.lblResult.text = result
        }
    }*/
}