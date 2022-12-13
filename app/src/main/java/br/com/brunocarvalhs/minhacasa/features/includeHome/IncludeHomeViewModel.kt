package br.com.brunocarvalhs.minhacasa.features.includeHome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.minhacasa.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IncludeHomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    private val mutableState = MutableLiveData<IncludeHomeViewState>()
    val state: LiveData<IncludeHomeViewState> = mutableState

    fun include(token: String) {
        viewModelScope.launch {
            try {
                mutableState.value = IncludeHomeViewState.Loading
                val result = repository.include(token)
                result?.let { mutableState.value = IncludeHomeViewState.Success(it) }
            } catch (error: Exception) {
                mutableState.value = IncludeHomeViewState.Error(error.message ?: "")
            }
        }
    }
}