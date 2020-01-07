package dev.claucookielabs.pasbuk.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.claucookielabs.pasbuk.model.Passbook
import dev.claucookielabs.pasbuk.model.PassesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PassListViewModel(private val passesRepository: PassesRepository) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _data = MutableLiveData<List<Passbook>>()
    val data: LiveData<List<Passbook>>
        get() = _data

    fun refresh() {
        GlobalScope.launch(Dispatchers.Main) {
            _loading.value = true
            _data.value = withContext(Dispatchers.IO) { passesRepository.mockPasses() }
            _loading.value = false
        }
    }

}

@Suppress("UNCHECKED_CAST")
class PassListViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PassListViewModel(PassesRepository()) as T
    }
}
