package dev.claucookielabs.pasbuk.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.scale
import dev.claucookielabs.pasbuk.R
import dev.claucookielabs.pasbuk.model.InfoField
import dev.claucookielabs.pasbuk.model.Passbook
import dev.claucookielabs.pasbuk.model.TextAlignment
import kotlinx.android.synthetic.main.activity_pass_detail.*

/**
 * This class will display the relevant information about the pass:
 * It will have 5 sections:
 * - Header: Background image, small barcode and header info. This will be placed inside a cardview.
 * - Primary content: The front information of the pass, the most relevant.
 * - Auxiliary content: The still comonly used information provided by the pass.
 * - Back content: Information rarely needed to use the pass, but still needs to be displayed.
 * - Map: If the pass has a location, show it here. (Extra mile, not available in first iteration)
 *
 * @startuml
 * PassDetailActivity --|> AppCompatActivity
 * @enduml
 */
class PassDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pass_detail)
        setupToolbar()
        val passbook = intent.getParcelableExtra<Passbook>(Passbook::class.java.name)!!
        renderPass(passbook)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(tool_bar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    private fun renderPass(passbook: Passbook) {
        passbook.primaryFields.forEach {
            fields_box.addView(
                createFieldTextView(it)
            )
        }
        passbook.secondaryFields.forEach {
            fields_box.addView(
                createFieldTextView(it)
            )
        }
    }

    private fun createFieldTextView(field: InfoField): TextView {
        val headerText = TextView(this, null, 0, R.style.Body1)
        headerText.textAlignment = getTextAlignment(field)
        headerText.typeface = ResourcesCompat.getFont(this, R.font.product_sans)
        headerText.text = buildSpannedString {
            bold { appendln(field.label.trimEnd()) }
            scale(1.3f) { append(field.value.trimEnd()) }
        }
        return headerText
    }

    private fun getTextAlignment(field: InfoField): Int {
        return when (field.textAlignment) {
            TextAlignment.Left -> View.TEXT_ALIGNMENT_VIEW_START
            TextAlignment.Center -> View.TEXT_ALIGNMENT_CENTER
            TextAlignment.Right -> View.TEXT_ALIGNMENT_VIEW_END
            TextAlignment.Natural -> View.TEXT_ALIGNMENT_INHERIT
        }
    }
}
