package com.example.examen_moviles

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var txtNombre: EditText
    private lateinit var txtApellido: EditText
    private lateinit var txtTelefono: EditText
    private lateinit var btnGuardar: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var personaAdapter: PersonaAdapter

    private val listaPersonas = mutableListOf<Persona>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtNombre = findViewById(R.id.txtNombre)
        txtApellido = findViewById(R.id.txtApellido)
        txtTelefono = findViewById(R.id.txtTelefono)
        btnGuardar = findViewById(R.id.btnGuardar)
        recyclerView = findViewById(R.id.recyclerView)

        personaAdapter = PersonaAdapter(listaPersonas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = personaAdapter

        btnGuardar.setOnClickListener {
            val nombre = txtNombre.text.toString().trim()
            val apellido = txtApellido.text.toString().trim()
            val telefono = txtTelefono.text.toString().trim()

            if (nombre.isNotEmpty() && apellido.isNotEmpty() && telefono.isNotEmpty()) {
                listaPersonas.add(Persona(nombre, apellido, telefono))
                personaAdapter.notifyItemInserted(listaPersonas.size - 1)

                txtNombre.text.clear()
                txtApellido.text.clear()
                txtTelefono.text.clear()
            }
        }
    }
}