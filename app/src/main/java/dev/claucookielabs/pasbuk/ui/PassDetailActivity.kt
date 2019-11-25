package dev.claucookielabs.pasbuk.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.claucookielabs.pasbuk.R

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
    }
}