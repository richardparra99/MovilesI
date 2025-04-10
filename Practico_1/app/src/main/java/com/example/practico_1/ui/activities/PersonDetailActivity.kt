package com.example.practico_1.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practico_1.R
import com.example.practico_1.databinding.ActivityPersonDetailBinding
import com.example.practico_1.ui.Models.Person
import com.example.practico_1.ui.Repositories.PersonRepository

class PersonDetailActivity : AppCompatActivity() {
    private var person: Person? = null
    private var selectodColor: Int =  0xFFFFFFFF.toInt()
    private lateinit var binding: ActivityPersonDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPersonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        person = intent.getSerializableExtra(PARAM_PERSON) as Person?
        loadPersonDetails(person)
        setupEventListeners()
    }

    private fun setupEventListeners() {
        binding.btnCancel.setOnClickListener { finish() }
        binding.btnSavePerson.setOnClickListener { savePerson() }
        binding.btnDelete.setOnClickListener { deletePerson() }
        binding.btn1.setOnClickListener { selectodColor = 0xFFF44336.toInt() }
        binding.btn2.setOnClickListener { selectodColor = 0xFF4CAF50.toInt() }
        binding.btn3.setOnClickListener { selectodColor = 0xFF2196F3.toInt() }
        binding.btn4.setOnClickListener { selectodColor = 0xFFFF9800.toInt() }
        binding.btn5.setOnClickListener { selectodColor = 0xFFE91E63.toInt() }
        binding.btn6.setOnClickListener { selectodColor = 0xFFFFEB3B.toInt() }
        binding.btn7.setOnClickListener { selectodColor = 0xFF795548.toInt() }
        binding.btn8.setOnClickListener { selectodColor = 0xFF808000.toInt() }
        binding.btn9.setOnClickListener { selectodColor = 0xFF4FC3F7.toInt() }
        binding.btn10.setOnClickListener { selectodColor = 0xFFCE93D8.toInt() }
    }

    private fun deletePerson() {
        PersonRepository.deletePerson(
            Person(
                person?.id?:0,
                binding.txtTitle.editText?.text.toString(),
                binding.txtDescription.editText?.text.toString(),
                binding.txtEstado.editText?.text.toString()
            )
        )
        finish()
    }

    private fun savePerson() {
        PersonRepository.savePerson(
            Person(
                person?.id?:0,
                binding.txtTitle.editText?.text.toString(),
                binding.txtDescription.editText?.text.toString(),
                binding.txtEstado.editText?.text.toString(),
                selectodColor
            )
        )
        finish()
    }

    private fun loadPersonDetails(person: Person?) {
        if (person == null || person.id == 0) {
            binding.btnDelete.isEnabled = false
            binding.btnDelete.isClickable = false
            return
        }
        binding.txtTitle.editText?.setText(person.titulo)
        binding.txtDescription.editText?.setText(person.description)
        binding.txtEstado.editText?.setText(person.estado)
    }

    companion object {
        const val PARAM_PERSON = "person"
        fun detailIntent(context: Context, person: Person): Intent? {
            val intent =Intent(context, PersonDetailActivity::class.java)
            intent.putExtra(PARAM_PERSON, person)
            return intent
        }
    }
}