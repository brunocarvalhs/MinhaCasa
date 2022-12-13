package br.com.brunocarvalhs.minhacasa.features.listCosts.viewModel

sealed class CostsViewState {
    data class Error(val error: String) : CostsViewState()
    object Success : CostsViewState()
    object Loading : CostsViewState()
}
