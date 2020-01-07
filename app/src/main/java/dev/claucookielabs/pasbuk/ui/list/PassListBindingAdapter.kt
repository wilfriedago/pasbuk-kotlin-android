package dev.claucookielabs.pasbuk.ui.list

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("items")
fun RecyclerView.setItems(passesUiModel: PassesUiModel?) {
    (passesUiModel as? PassesUiModel.Content)?.passes?.let { items ->
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
}

@BindingAdapter("failed")
fun View.setFailed(passesUiModel: PassesUiModel?) {
    visibility = passesUiModel?.let {
        if (passesUiModel is PassesUiModel.Error) View.VISIBLE else View.GONE
    } ?: View.GONE
}

@BindingAdapter("loading")
fun View.setLoading(passesUiModel: PassesUiModel?) {
    visibility = passesUiModel?.let {
        if (passesUiModel is PassesUiModel.Loading) View.VISIBLE else View.GONE
    } ?: View.GONE
}

