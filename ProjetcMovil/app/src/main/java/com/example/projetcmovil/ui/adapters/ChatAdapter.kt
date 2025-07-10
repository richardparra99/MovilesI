package com.example.projetcmovil.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projetcmovil.data.model.MensajeChat
import com.example.projetcmovil.databinding.ItemMensajeClienteBinding
import com.example.projetcmovil.databinding.ItemMensajeTrabajadorBinding

class ChatAdapter(private var lista: List<MensajeChat>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TIPO_CLIENTE = 0
        private const val TIPO_TRABAJADOR = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (lista[position].enviadoPorCliente) TIPO_CLIENTE else TIPO_TRABAJADOR
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TIPO_CLIENTE) {
            val binding = ItemMensajeClienteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            MensajeClienteViewHolder(binding)
        } else {
            val binding = ItemMensajeTrabajadorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            MensajeTrabajadorViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val mensaje = lista[position]
        if (holder is MensajeClienteViewHolder) {
            holder.binding.tvMensajeCliente.text = mensaje.mensaje
        } else if (holder is MensajeTrabajadorViewHolder) {
            holder.binding.tvMensajeTrabajador.text = mensaje.mensaje
        }
    }

    override fun getItemCount() = lista.size

    fun actualizarMensajes(nuevaLista: List<MensajeChat>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }

    class MensajeClienteViewHolder(val binding: ItemMensajeClienteBinding) : RecyclerView.ViewHolder(binding.root)
    class MensajeTrabajadorViewHolder(val binding: ItemMensajeTrabajadorBinding) : RecyclerView.ViewHolder(binding.root)
}