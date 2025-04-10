package com.example.practico_1.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practico_1.databinding.PersonItemLayoutBinding
import com.example.practico_1.ui.Models.Person

class PersonAdapter(
   var people: ArrayList<Person>
): RecyclerView.Adapter<PersonAdapter.ViewHolder>() {
    var persoonClickListerner: PersonClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PersonItemLayoutBinding.inflate(
            inflater,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return people.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = people[position]
        holder.bind(item, persoonClickListerner)
    }

    fun setOnPersonClickListener(listener: PersonClickListener){
        persoonClickListerner = listener
    }

    fun setData(people: java.util.ArrayList<Person>) {
        this.people = people
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: PersonItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(item: Person, listener: PersonClickListener?) {
            binding.lblContactTitle.text = item.titulo
            binding.lblContactDescription.text = item.description
            binding.lblContactEstado.text = item.estado
            binding.root.setOnClickListener{
                listener?.onPersonClick(item)
            }
            binding.btnContactOpenDetail.setOnClickListener {
                listener?.onPersonDetailClick(item)
            }
            binding.btnCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    binding.lblContactTitle.paintFlags = binding.lblContactTitle.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
                    binding.lblContactDescription.paintFlags = binding.lblContactDescription.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
                    binding.lblContactEstado.paintFlags =binding.lblContactEstado.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    binding.lblContactTitle.paintFlags = binding.lblContactTitle.paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    binding.lblContactDescription.paintFlags = binding.lblContactDescription.paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    binding.lblContactEstado.paintFlags = binding.lblContactEstado.paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }
            binding.root.setBackgroundColor(item.color)
        }

    }

    interface PersonClickListener {
        fun onPersonClick(person: Person)
        fun onPersonDetailClick(person: Person)
    }
}