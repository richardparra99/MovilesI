package com.example.projetcmovil.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetcmovil.R
import com.example.projetcmovil.data.model.Categoria
import java.util.Locale

class CategoriaAdapter (
    private val onClick: (Categoria) -> Unit
) : RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder>() {

    private var listaCompleta: List<Categoria> = emptyList()
    private var listaFiltrada: List<Categoria> = emptyList()

    fun actualizarLista(nuevaLista: List<Categoria>) {
        listaCompleta = nuevaLista
        listaFiltrada = nuevaLista
        notifyDataSetChanged()
    }

    fun filtrar(texto: String) {
        listaFiltrada = if (texto.isEmpty()) {
            listaCompleta
        } else {
            listaCompleta.filter {
                it.nombre.lowercase(Locale.getDefault()).contains(texto.lowercase(Locale.getDefault()))
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_categoria, parent, false)
        return CategoriaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        holder.bind(listaFiltrada[position])
    }

    override fun getItemCount(): Int = listaFiltrada.size

    inner class CategoriaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNombreCategoria: TextView = itemView.findViewById(R.id.tvNombreCategoria)

        fun bind(categoria: Categoria) {
            tvNombreCategoria.text = categoria.nombre
            itemView.setOnClickListener { onClick(categoria) }
        }
    }
}