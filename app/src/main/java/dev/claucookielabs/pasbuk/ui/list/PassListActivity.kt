package dev.claucookielabs.pasbuk.ui.list

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import dev.claucookielabs.pasbuk.R
import dev.claucookielabs.pasbuk.model.Passbook
import dev.claucookielabs.pasbuk.model.PassesListUiModel
import dev.claucookielabs.pasbuk.ui.detail.PassDetailActivity
import dev.claucookielabs.pasbuk.ui.extensions.show
import kotlinx.android.synthetic.main.activity_pass_list.*

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
 * PassListActivity --|> AppCompatActivity
 * @enduml
 */
class PassListActivity : AppCompatActivity() {

    private val passAdapter = PassesAdapter { openPass(it) }
    // Extension function to pass the viewmodel factory and instantiate the viewmodel
    private val viewModel by viewModels<PassListViewModel> { PassListViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pass_list)
        setupToolbar()
        setupRecyclerView()
        viewModel.data.observe(this, Observer(::updateUi))
    }

    private fun setupToolbar() {
        setSupportActionBar(tool_bar)
        supportActionBar?.title = ""
    }

    private fun setupRecyclerView() {
        passes_rv.apply {
            layoutManager = LinearLayoutManager(this@PassListActivity)
            adapter = passAdapter
            addItemDecoration(DividerItemDecoration(context, VERTICAL))
        }
    }

    private fun openPass(pass: Passbook) {
        val intent = Intent(this, PassDetailActivity::class.java)
        intent.putExtra(Passbook::class.java.name, pass)
        startActivity(intent)
    }

    private fun updateUi(passesListUiModel: PassesListUiModel) {
        loading_view.show(passesListUiModel is PassesListUiModel.Loading)
        error_view.show(passesListUiModel is PassesListUiModel.Error)
        passes_rv.show(passesListUiModel is PassesListUiModel.Content)

        if (passesListUiModel is PassesListUiModel.Content) {
            passAdapter.passes = passesListUiModel.passes
            passAdapter.notifyDataSetChanged()
        }
    }

}
