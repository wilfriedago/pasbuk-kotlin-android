package dev.claucookielabs.pasbuk.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.claucookielabs.pasbuk.model.PassesRepository
import dev.claucookielabs.pasbuk.model.PassesListUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PassListViewModel(private val passesRepository: PassesRepository) : ViewModel() {

    private val _data = MutableLiveData<PassesListUiModel>()
    val data: LiveData<PassesListUiModel>
        get() {
            if (_data.value == null) refresh()
            return _data
        }

    private fun refresh() {
        GlobalScope.launch(Dispatchers.Main) {
            _data.value = PassesListUiModel.Loading
            _data.value = withContext(Dispatchers.IO) { passesRepository.mockPasses() }
        }
    }

}

@Suppress("UNCHECKED_CAST")
class PassListViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PassListViewModel(PassesRepository()) as T
    }
}
