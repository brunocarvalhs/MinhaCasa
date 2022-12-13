package br.com.brunocarvalhs.minhacasa.features.registerCosts

import androidx.lifecycle.*
import br.com.brunocarvalhs.minhacasa.domain.entities.Cost
import br.com.brunocarvalhs.minhacasa.domain.entities.Home
import br.com.brunocarvalhs.minhacasa.domain.repository.CostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterCostsViewModel @Inject constructor(
    private val repository: CostRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val home: Home = RegisterCostsFragmentArgs.fromSavedStateHandle(savedStateHandle).home

    private val mutableState = MutableLiveData<RegisterCostsViewState>()
    val state: LiveData<RegisterCostsViewState> = mutableState

    fun create(cost: Cost) {
        viewModelScope.launch {
            try {
                mutableState.value = RegisterCostsViewState.Loading
                repository.add(home, cost)
                mutableState.value = RegisterCostsViewState.Success(home, cost)
            } catch (error: Exception) {
                mutableState.value = RegisterCostsViewState.Error(error.message ?: "")
            }
        }
    }
}