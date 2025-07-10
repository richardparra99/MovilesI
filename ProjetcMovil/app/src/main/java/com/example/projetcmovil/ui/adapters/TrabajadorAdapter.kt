package com.example.projetcmovil.ui.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projetcmovil.data.model.Trabajador
import com.example.projetcmovil.databinding.ItemTrabajadorBinding


class TrabajadorAdapter(
    private val onClick: (Trabajador) -> Unit
) : RecyclerView.Adapter<TrabajadorAdapter.TrabajadorViewHolder>() {

    private var lista: List<Trabajador> = emptyList()

    fun actualizarLista(nuevaLista: List<Trabajador>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrabajadorViewHolder {
        val binding = ItemTrabajadorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrabajadorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrabajadorViewHolder, position: Int) {
        holder.bind(lista[position])
    }

    override fun getItemCount(): Int = lista.size

    inner class TrabajadorViewHolder(private val binding: ItemTrabajadorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(trabajador: Trabajador) {
            binding.tvNombreTrabajador.text = "${trabajador.user?.profile?.name} ${trabajador.user?.profile?.last_name}"
            binding.tvRatingTrabajador.text = "${trabajador.reviews_count} trabajos"
            Glide.with(binding.root).load(trabajador.picture_url).into(binding.imgTrabajador)

            binding.root.setOnClickListener {
                onClick(trabajador)
            }
        }
    }
}