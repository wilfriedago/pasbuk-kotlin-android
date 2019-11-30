package dev.claucookielabs.pasbuk.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import dev.claucookielabs.pasbuk.R
import dev.claucookielabs.pasbuk.model.PassesRepository
import kotlinx.android.synthetic.main.activity_main.*

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
    private val passesRepository = PassesRepository()

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
        passAdapter = PassesAdapter()
        passes_rv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = passAdapter
            addItemDecoration(DividerItemDecoration(context, VERTICAL))
        }
    }

    private fun loadPasses() {
        // Load mock passes for now
        val passes = passesRepository.mockPasses()
        passAdapter.passes = passes
        passAdapter.notifyDataSetChanged()
    }

}

