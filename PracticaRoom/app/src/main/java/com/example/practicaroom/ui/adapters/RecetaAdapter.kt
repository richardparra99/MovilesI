

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaroom.databinding.PersonItemLayoutBinding
import com.example.practicaroom.db.models.Receta

class RecetaAdapter(
    var people: ArrayList<Receta>
): RecyclerView.Adapter<RecetaAdapter.ViewHolder>() {
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

    /*override fun setOnPersonClickListener(listener: PersonClickListener){
        persoonClickListerner = listener
    }*/

    fun setData(people: java.util.ArrayList<Receta>) {
        this.people = people
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: PersonItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(item: Receta, listener: PersonClickListener?) {
            binding.lblRectaTitulo.text = item.titulo
            binding.lblIngredientes.text = item.ingredientes
            binding.lblPreparacion.text = item.preparacion
            binding.root.setOnClickListener{
                listener?.onRecetaClick(item)
            }
            binding.btnContactOpenDetail.setOnClickListener {
                listener?.onPersonDetailClick(item)
            }
            /*binding.btnCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    binding.lblRectaTitulo.paintFlags = binding.lblContactTitle.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
                    binding.lblIngredientes.paintFlags = binding.lblContactDescription.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
                    binding.lblPreparacion.paintFlags =binding.lblContactEstado.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    binding.lblContactTitle.paintFlags = binding.lblContactTitle.paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    binding.lblContactDescription.paintFlags = binding.lblContactDescription.paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    binding.lblContactEstado.paintFlags = binding.lblContactEstado.paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }*/
            //binding.root.setBackgroundColor(item.color)
        }

    }

    interface PersonClickListener {
        fun onRecetaClick(receta: Receta)
        fun onPersonDetailClick(receta: Receta)
    }
}