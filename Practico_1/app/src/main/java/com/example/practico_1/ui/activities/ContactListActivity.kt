package com.example.practico_1.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practico_1.R
import com.example.practico_1.databinding.ActivityContactListBinding
import com.example.practico_1.ui.Models.Person
import com.example.practico_1.ui.Repositories.PersonRepository
import com.example.practico_1.ui.adapters.PersonAdapter

class ContactListActivity : AppCompatActivity(), PersonAdapter.PersonClickListener {
    private lateinit var binding: ActivityContactListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityContactListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupRecyclerView()
        setupEventListeners()
    }

    private fun setupEventListeners() {
        binding.btnCreatelist.setOnClickListener {
            val intent = PersonDetailActivity.detailIntent(this, Person(0, "", "","",0))
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        reloadData()
    }

    private fun reloadData() {
        val people = PersonRepository.getPeaple()
        val adapter = binding.rvContactList.adapter as PersonAdapter
        adapter.setData(people)
    }


    private fun setupRecyclerView() {
        val adapter = PersonAdapter(PersonRepository.getPeaple())
        binding.rvContactList.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(this@ContactListActivity).apply {
                orientation = RecyclerView.VERTICAL
            }
            addItemDecoration(
                DividerItemDecoration(
                    this@ContactListActivity,
                    LinearLayoutManager.VERTICAL
                )
            )
        }
        adapter.setOnPersonClickListener(this)
    }

    override fun onPersonClick(person: Person) {
        Toast.makeText(this, "click en la tarea de: ${person.id}", Toast.LENGTH_SHORT).show()
    }

    override fun onPersonDetailClick(person: Person) {
        val intent = PersonDetailActivity.detailIntent(this, person)
        startActivity(intent)
    }
}