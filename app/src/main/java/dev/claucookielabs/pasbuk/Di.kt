package dev.claucookielabs.pasbuk

import dev.claucookielabs.pasbuk.model.PassesRepository
import dev.claucookielabs.pasbuk.ui.detail.PassDetailActivity
import dev.claucookielabs.pasbuk.ui.detail.PassDetailViewModel
import dev.claucookielabs.pasbuk.ui.list.PassListActivity
import dev.claucookielabs.pasbuk.ui.list.PassListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun App.initKoin() {
    startKoin {
        androidLogger(Level.DEBUG)
        androidContext(this@initKoin)
        modules(appModule, scopedModule)
    }
}

private val appModule = module {
    single { PassesRepository() }
}

private val scopedModule = module {
    scope(named<PassListActivity>()) {
        viewModel { PassListViewModel(get()) }
    }

    scope(named<PassDetailActivity>()) {
        viewModel { PassDetailViewModel() }
    }
}
