package dev.claucookielabs.pasbuk.passlist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.claucookielabs.pasbuk.common.domain.BaseRequest
import dev.claucookielabs.pasbuk.common.domain.model.Passbook
import dev.claucookielabs.pasbuk.passlist.domain.GetAllPasses
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PassListViewModel(
    private val getAllPasses: GetAllPasses
) : ViewModel() {

    private val _data = MutableLiveData<PassesUiModel>()
    val data: LiveData<PassesUiModel>
        get() = _data


    fun refresh() {
        viewModelScope.launch {
            _data.value =
                PassesUiModel.Loading
            val result = withContext(IO) {
                PassesUiModel.Content(getAllPasses.execute(BaseRequest()))
            }
            handleResult(result)
        }
    }

    private fun handleResult(result: PassesUiModel) {
        _data.value = result
    }

}

sealed class PassesUiModel {
    data class Content(val passes: List<Passbook>) : PassesUiModel()
    object Loading : PassesUiModel()
    object Error : PassesUiModel()

}
