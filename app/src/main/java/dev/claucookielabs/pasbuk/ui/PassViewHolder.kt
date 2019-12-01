package dev.claucookielabs.pasbuk.ui

import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.scale
import androidx.recyclerview.widget.RecyclerView
import dev.claucookielabs.pasbuk.R
import dev.claucookielabs.pasbuk.model.InfoField
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
            val headerText = createHeaderTextView(header)
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

    private fun createHeaderTextView(header: InfoField): TextView {
        val headerText = TextView(itemView.context, null, 0, R.style.Body1)
        headerText.gravity = Gravity.END
        headerText.textAlignment = View.TEXT_ALIGNMENT_VIEW_END
        headerText.typeface = ResourcesCompat.getFont(itemView.context, R.font.product_sans)
        headerText.text = buildSpannedString {
            bold { appendln(header.label) }
            scale(1.3f) { append(header.value) }
        }
        return headerText
    }
}
