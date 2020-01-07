package dev.claucookielabs.pasbuk.ui.list

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.claucookielabs.pasbuk.model.Passbook

@BindingAdapter("items")
fun RecyclerView.setItems(items: List<Passbook>?) {
    (adapter as? PassListAdapter)?.let {
        items?.let { newItems ->
            val oldItems = it.passes
            val itemsDiff = DiffUtil.calculateDiff(
                object : DiffUtil.Callback() {
                    override fun getOldListSize(): Int = oldItems.size

                    override fun getNewListSize(): Int = newItems.size

                    override fun areContentsTheSame(
                        oldItemPosition: Int,
                        newItemPosition: Int
                    ): Boolean = oldItems[oldItemPosition] == newItems[newItemPosition]

                    override fun areItemsTheSame(
                        oldItemPosition: Int,
                        newItemPosition: Int
                    ): Boolean =
                        oldItems[oldItemPosition] == items[newItemPosition]

                })
            it.passes = newItems
            itemsDiff.dispatchUpdatesTo(it)
        }
    }
}

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean?) {
    visibility = visible?.let {
        if (visible) View.VISIBLE else View.GONE
    } ?: View.GONE
}
