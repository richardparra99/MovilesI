package com.example.projetcmovil.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projetcmovil.data.model.Cita
import com.example.projetcmovil.databinding.ItemCitaBinding

class CitaAdapter(
    private val citas: List<Cita>
) : RecyclerView.Adapter<CitaAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemCitaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cita: Cita) {
            binding.txtFecha.text = cita.fecha
            binding.txtDescripcion.text = cita.descripcion
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCitaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(citas[position])
    }

    override fun getItemCount(): Int = citas.size
}