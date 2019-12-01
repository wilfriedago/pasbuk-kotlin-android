package dev.claucookielabs.pasbuk.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import dev.claucookielabs.pasbuk.R
import dev.claucookielabs.pasbuk.model.Passbook
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

    }
}
