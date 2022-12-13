package br.com.brunocarvalhs.minhacasa.features.registerCosts

import br.com.brunocarvalhs.minhacasa.domain.entities.Cost
import br.com.brunocarvalhs.minhacasa.domain.entities.Home

sealed class RegisterCostsViewState {
    data class Success(val home: Home, val cost: Cost) : RegisterCostsViewState()
    data class Error(val error: String) : RegisterCostsViewState()
    object Loading : RegisterCostsViewState()
}
