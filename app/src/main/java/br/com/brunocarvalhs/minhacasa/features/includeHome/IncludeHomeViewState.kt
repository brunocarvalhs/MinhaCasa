package br.com.brunocarvalhs.minhacasa.features.includeHome

import br.com.brunocarvalhs.minhacasa.domain.entities.Home

sealed class IncludeHomeViewState {
    data class Success(val home: Home) : IncludeHomeViewState()
    data class Error(val error: String) : IncludeHomeViewState()
    object Loading : IncludeHomeViewState()
}
