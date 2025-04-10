package com.example.practico_1.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practico_1.databinding.NamesListItemBinding

class NamesAdapter(
    var names: ArrayList<String>
): RecyclerView.Adapter<NamesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NamesListItemBinding.inflate(
            inflater,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return names.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = names[position]
        holder.bind(item)
    }

    class ViewHolder(val binding: NamesListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.lblPersonName.text = item
        }
    }
}