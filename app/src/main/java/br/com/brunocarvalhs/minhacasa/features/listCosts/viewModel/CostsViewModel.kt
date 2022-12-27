package br.com.brunocarvalhs.minhacasa.features.listCosts.viewModel

import android.content.SharedPreferences
import androidx.lifecycle.*
import br.com.brunocarvalhs.minhacasa.BuildConfig
import br.com.brunocarvalhs.minhacasa.domain.entities.Cost
import br.com.brunocarvalhs.minhacasa.domain.enums.Type
import br.com.brunocarvalhs.minhacasa.domain.repository.CostRepository
import br.com.brunocarvalhs.minhacasa.features.listCosts.CostsFragmentArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CostsViewModel @Inject constructor(
    private val repository: CostRepository,
    private val sharedPreferences: SharedPreferences,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val mutableState = MutableLiveData<CostsViewState>()
    val state: LiveData<CostsViewState> = mutableState

    val home = CostsFragmentArgs.fromSavedStateHandle(savedStateHandle).home
    val listFix = mutableListOf<Cost>()
    val listVar = mutableListOf<Cost>()

    fun fetchData() {
        viewModelScope.launch {
            try {
                mutableState.value = CostsViewState.Loading
                val result = repository.list(home)
                filterListFix(result)
                filterListVar(result)
                mutableState.value = CostsViewState.Success
            } catch (error: Exception) {
                mutableState.value = CostsViewState.Error(error.message ?: "")
            }
        }
    }

    fun cleanHome() {
        sharedPreferences.edit().putString(BuildConfig.APPLICATION_ID, null).apply()
    }

    fun calculationValueAll(): Double {
        val valueAllFix = listFix.sumOf { it.value }
        val valueAllVar = listVar.sumOf { it.value }
        return (valueAllFix + valueAllVar)
    }

    private fun filterListFix(list: List<Cost>) {
        listFix.clear()
        listFix.addAll(list.filter { it.type == Type.Fix })
    }

    private fun filterListVar(list: List<Cost>) {
        listVar.clear()
        listVar.addAll(list.filter { it.type == Type.Variable })
    }
}