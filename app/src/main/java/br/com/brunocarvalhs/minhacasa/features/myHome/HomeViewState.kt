package br.com.brunocarvalhs.minhacasa.features.myHome

import br.com.brunocarvalhs.minhacasa.domain.entities.Home

sealed class HomeViewState {
    data class Screen(val home: List<Home>) : HomeViewState()
    data class Error(val error: String) : HomeViewState()
    object Loading : HomeViewState()
}