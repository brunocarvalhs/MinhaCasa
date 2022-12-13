package br.com.brunocarvalhs.minhacasa.features.registerHome

import br.com.brunocarvalhs.minhacasa.domain.entities.Home

sealed class RegisterHomeViewState {
    data class Success(val home: Home) : RegisterHomeViewState()
    data class Error(val error: String) : RegisterHomeViewState()
    object Loading : RegisterHomeViewState()
}
