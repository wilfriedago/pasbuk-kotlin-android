package dev.claucookielabs.pasbuk.ui.list

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.claucookielabs.pasbuk.model.Passbook

@BindingAdapter("items")
fun RecyclerView.setItems(passes: List<Passbook>?) {
    (adapter as? PassListAdapter)?.let {
        it.passes = passes ?: emptyList()
    }
}
