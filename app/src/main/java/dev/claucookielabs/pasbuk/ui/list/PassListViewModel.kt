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

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean>
        get() = _error


    fun refresh() {
        GlobalScope.launch(Dispatchers.Main) {
            _loading.value = true
            try {
                _error.value = false
                _data.value = withContext(Dispatchers.IO) { passesRepository.mockPasses() }
            } catch (exception: IllegalStateException) {
                _data.value = emptyList()
                _error.value = true
            }
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
