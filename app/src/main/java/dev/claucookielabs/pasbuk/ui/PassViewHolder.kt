package dev.claucookielabs.pasbuk.ui

import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.recyclerview.widget.RecyclerView
import dev.claucookielabs.pasbuk.R
import dev.claucookielabs.pasbuk.model.Passbook
import dev.claucookielabs.pasbuk.ui.extensions.addRipple
import kotlinx.android.synthetic.main.item_view_pass.view.*


class PassViewHolder(
    itemView: View,
    private val onClickAction: (Passbook) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    fun bind(pass: Passbook) {
        pass.headers.forEach { header ->
            val layoutParams = createHeaderLayoutParams()
            val headerText = createHeaderTextView()

            headerText.text = buildSpannedString {
                bold { appendln(header.label) }
                append(header.value)
            }
            itemView.pass_row_headers.addView(
                headerText,
                layoutParams
            )
            itemView.addRipple()
            itemView.setOnClickListener {
                onClickAction(pass)
            }
        }
    }

    private fun createHeaderLayoutParams(): LayoutParams {
        val layoutParams = LinearLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        layoutParams.marginStart =
            itemView.resources.getDimensionPixelSize(R.dimen.regular_spacing)
        return layoutParams
    }

    private fun createHeaderTextView(): TextView {
        val headerText = TextView(itemView.context)
        headerText.gravity = Gravity.END
        headerText.textAlignment = View.TEXT_ALIGNMENT_VIEW_END
        headerText.setTextAppearance(R.style.Body1)
        return headerText
    }
}
