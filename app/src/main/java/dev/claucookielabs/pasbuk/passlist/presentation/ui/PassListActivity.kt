package dev.claucookielabs.pasbuk.passlist.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import dev.claucookielabs.pasbuk.R
import dev.claucookielabs.pasbuk.common.domain.model.Passbook
import dev.claucookielabs.pasbuk.databinding.ActivityPassListBinding
import dev.claucookielabs.pasbuk.passdetail.presentation.ui.PassDetailActivity
import dev.claucookielabs.pasbuk.passdownload.presentation.ui.PassDownloadActivity
import dev.claucookielabs.pasbuk.passdownload.services.PassDownloadService
import dev.claucookielabs.pasbuk.passlist.presentation.PassListViewModel
import kotlinx.android.synthetic.main.activity_pass_list.*
import org.koin.androidx.scope.lifecycleScope
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

    private val passesAdapter =
        PassListAdapter {
            openPass(
                it
            )
        }
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
