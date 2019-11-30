package dev.claucookielabs.pasbuk.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.claucookielabs.pasbuk.R
import dev.claucookielabs.pasbuk.model.Passbook

class PassesAdapter(
    var passes: List<Passbook> = emptyList()
) : RecyclerView.Adapter<PassViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_view_pass,
            parent,
            false
        )
        return PassViewHolder(itemView)
    }

    override fun getItemCount(): Int = passes.size

    override fun onBindViewHolder(holder: PassViewHolder, position: Int) {
        val pass = passes[position]
        holder.bind(pass)
    }

}
