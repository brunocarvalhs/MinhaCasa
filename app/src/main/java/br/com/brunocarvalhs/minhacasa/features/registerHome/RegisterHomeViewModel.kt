package br.com.brunocarvalhs.minhacasa.features.registerHome

import androidx.lifecycle.*
import br.com.brunocarvalhs.minhacasa.domain.entities.Home
import br.com.brunocarvalhs.minhacasa.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterHomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val mutableState = MutableLiveData<RegisterHomeViewState>()
    val state: LiveData<RegisterHomeViewState> = mutableState

    fun create(home: Home) {
        viewModelScope.launch {
            try {
                mutableState.value = RegisterHomeViewState.Loading
                repository.add(home)
                mutableState.value = RegisterHomeViewState.Success(home)
            } catch (error: Exception) {
                mutableState.value = RegisterHomeViewState.Error(error.message ?: "")
            }
        }
    }
}