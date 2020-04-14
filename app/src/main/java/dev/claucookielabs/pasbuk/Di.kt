package dev.claucookielabs.pasbuk

import androidx.preference.PreferenceManager
import androidx.room.Room
import com.google.gson.Gson
import dev.claucookielabs.pasbuk.common.data.datasource.PassesDatasource
import dev.claucookielabs.pasbuk.common.data.datasource.local.PassbookDatabase
import dev.claucookielabs.pasbuk.common.data.datasource.local.LocalDataSource
import dev.claucookielabs.pasbuk.common.data.repository.PassesRepository
import dev.claucookielabs.pasbuk.passdetail.presentation.PassDetailViewModel
import dev.claucookielabs.pasbuk.passdetail.presentation.ui.PassDetailActivity
import dev.claucookielabs.pasbuk.passdownload.presentation.ui.PassDownloadActivity
import dev.claucookielabs.pasbuk.passdownload.services.IntentDataHelper
import dev.claucookielabs.pasbuk.passdownload.services.PassDownloadService
import dev.claucookielabs.pasbuk.passlist.domain.GetAllPasses
import dev.claucookielabs.pasbuk.passlist.presentation.PassListViewModel
import dev.claucookielabs.pasbuk.passlist.presentation.ui.PassListActivity
import org.koin.android.ext.koin.androidApplication
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
    single { PassesRepository(get()) }
    single { IntentDataHelper() }
    single { Gson() }
    single<PassesDatasource> { LocalDataSource(get()) }
    single {
        Room.databaseBuilder(
            androidApplication(),
            PassbookDatabase::class.java, "passbookdb"
        ).build()
    }
}

private val scopedModule = module {
    scope(named<PassListActivity>()) {
        viewModel { PassListViewModel(get()) }
        scoped { GetAllPasses(get()) }
    }

    scope(named<PassDetailActivity>()) {
        viewModel { PassDetailViewModel() }
    }

    scope(named<PassDownloadActivity>()) {

    }

    scope(named<PassDownloadService>()) {

    }
}
