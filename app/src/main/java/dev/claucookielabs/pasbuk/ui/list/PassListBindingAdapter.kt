package dev.claucookielabs.pasbuk.ui.list

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.claucookielabs.pasbuk.model.PassesListUiModel

@BindingAdapter("passes")
fun RecyclerView.setPasses(uiModel: PassesListUiModel?) {
    (adapter as? PassListAdapter)?.let {
        it.passes = if (uiModel is PassesListUiModel.Content) uiModel.passes else emptyList()
    }
}
