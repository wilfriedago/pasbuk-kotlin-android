package dev.claucookielabs.pasbuk.ui.list

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import dev.claucookielabs.pasbuk.R
import dev.claucookielabs.pasbuk.databinding.ActivityPassListBinding
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

    private val passAdapter = PassListAdapter { openPass(it) }
    // Extension function to pass the viewmodel factory and instantiate the viewmodel
    private val viewModel by viewModels<PassListViewModel> { PassListViewModelFactory() }
    private lateinit var binding : ActivityPassListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDataBinding()
        setupToolbar()
        setupRecyclerView()
    }

    private fun setupDataBinding() {
        binding = DataBindingUtil.setContentView( this, R.layout.activity_pass_list)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
    }

    private fun setupToolbar() {
        setSupportActionBar(tool_bar)
        supportActionBar?.title = ""
    }

    private fun setupRecyclerView() {
        binding.passesRv.apply {
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
        // TODO: Deal with this when data-finding is working.
        loading_view.show(passesListUiModel is PassesListUiModel.Loading)
        error_view.show(passesListUiModel is PassesListUiModel.Error)
        passes_rv.show(passesListUiModel is PassesListUiModel.Content)
    }

}
