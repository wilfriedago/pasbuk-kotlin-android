package dev.claucookielabs.pasbuk.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.claucookielabs.pasbuk.R
import dev.claucookielabs.pasbuk.model.Passbook

class PassesAdapter(
    private val onItemClickAction: (Passbook) -> Unit
) : RecyclerView.Adapter<PassViewHolder>() {

    var passes: List<Passbook> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_view_pass,
            parent,
            false
        )
        return PassViewHolder(itemView, onItemClickAction)
    }

    override fun getItemCount(): Int = passes.size

    override fun onBindViewHolder(holder: PassViewHolder, position: Int) {
        val pass = passes[position]
        holder.bind(pass)
    }

}
