package dev.claucookielabs.pasbuk.model

sealed class PassesListUiModel {
    object Loading : PassesListUiModel()
    object Error : PassesListUiModel()
    class Content(val passes: List<Passbook>) : PassesListUiModel()
}
