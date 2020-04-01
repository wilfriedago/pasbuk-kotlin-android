package dev.claucookielabs.pasbuk.ui.list

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import dev.claucookielabs.pasbuk.R
import dev.claucookielabs.pasbuk.databinding.ActivityPassListBinding
import dev.claucookielabs.pasbuk.model.Passbook
import dev.claucookielabs.pasbuk.ui.detail.PassDetailActivity
import kotlinx.android.synthetic.main.activity_pass_list.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.androidx.viewmodel.scope.viewModel

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

    private val passesAdapter = PassListAdapter { openPass(it) }
    private val passesViewModel: PassListViewModel by lifecycleScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDataBinding()
        setupToolbar()
        passesViewModel.refresh()
    }

    private fun setupDataBinding() {
        val binding: ActivityPassListBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_pass_list
        )
        binding.apply {
            viewmodel = passesViewModel
            lifecycleOwner = this@PassListActivity
            passesRv.apply {
                addItemDecoration(DividerItemDecoration(context, VERTICAL))
            }
            passesRv.adapter = passesAdapter
        }

    }

    private fun setupToolbar() {
        setSupportActionBar(tool_bar)
        supportActionBar?.title = ""
    }

    private fun openPass(pass: Passbook) {
        val intent = Intent(this, PassDetailActivity::class.java)
        intent.putExtra(Passbook::class.java.name, pass)
        startActivity(intent)
    }
}
