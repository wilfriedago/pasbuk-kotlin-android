package dev.claucookielabs.pasbuk.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.claucookielabs.pasbuk.R

/**
 * This class will display a list of currently valid passes.
 * Each row will show a pass header information. The date of the pass
 * should still be valid.
 * It will display as well the passes which date is null.
 * -
 * The bottom bar can be similar to the Keep app, holding a (+) button
 * and a few quick actions. The quick action can be Archived/Expired passes.
 *
 * @startuml
 * MainActivity --|> AppCompatActivity
 * @enduml
 */
class MainActivity : AppCompatActivity() {

    private lateinit var passAdapter: PassesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar()
        setupRecyclerView()
        loadPasses()
    }

    private fun setupToolbar() {
        // Transparent toolbar like Google Podcasts with title in the middle
        // and menu button on the right hand.
    }

    private fun setupRecyclerView() {
        // Setup the adapter and the layout manager (vertical)
        passAdapter = PassesAdapter()
    }

    private fun loadPasses() {
        // Request passes to the local storage (assets/passes  for now)
        // Update the UI with the result
    }
}

class PassesAdapter
