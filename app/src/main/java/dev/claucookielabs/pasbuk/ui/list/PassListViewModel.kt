package dev.claucookielabs.pasbuk.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.claucookielabs.pasbuk.model.Passbook
import dev.claucookielabs.pasbuk.model.PassesRepository
import dev.claucookielabs.pasbuk.ui.list.PassListViewModel.UiModel.Content

class PassListViewModel(private val passesRepository: PassesRepository) : ViewModel() {

    private val _data = MutableLiveData<UiModel>()
    val data: LiveData<UiModel>
        get() {
            if (_data.value == null) refresh()
            return _data
        }

    private fun refresh() {
        _data.value = Content(passesRepository.mockPasses())
    }

    sealed class UiModel {
        object Loading : UiModel()
        object Error : UiModel()
        class Content(val passes: List<Passbook>) : UiModel()
    }

}

@Suppress("UNCHECKED_CAST")
class PassListViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PassListViewModel(PassesRepository()) as T
    }
}
