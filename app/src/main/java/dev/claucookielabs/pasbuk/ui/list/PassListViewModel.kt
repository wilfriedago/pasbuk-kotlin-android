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

    private val _data = MutableLiveData<PassesUiModel>()
    val data: LiveData<PassesUiModel>
        get() = _data


    fun refresh() {
        GlobalScope.launch(Dispatchers.Main) {
            _data.value = PassesUiModel.Loading
            try {
                _data.value =
                    withContext(Dispatchers.IO) { PassesUiModel.Content(passesRepository.mockPasses()) }
            } catch (exception: IllegalStateException) {
                _data.value = PassesUiModel.Error
            }
        }
    }

}

sealed class PassesUiModel {
    data class Content(val passes: List<Passbook>) : PassesUiModel()
    object Loading : PassesUiModel()
    object Error : PassesUiModel()

}

@Suppress("UNCHECKED_CAST")
class PassListViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PassListViewModel(PassesRepository()) as T
    }
}
