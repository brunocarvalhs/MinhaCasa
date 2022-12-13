package br.com.brunocarvalhs.minhacasa.features.myHome

import android.content.SharedPreferences
import androidx.lifecycle.*
import br.com.brunocarvalhs.minhacasa.BuildConfig
import br.com.brunocarvalhs.minhacasa.domain.entities.Home
import br.com.brunocarvalhs.minhacasa.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val sharedPreferences: SharedPreferences,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val mutableState = MutableLiveData<HomeViewState>()
    val state: LiveData<HomeViewState> = mutableState

    fun fetchData() {
        viewModelScope.launch {
            try {
                val home = repository.list()
                mutableState.value = HomeViewState.Screen(home)
            } catch (error: Exception) {
                mutableState.value = HomeViewState.Error(error.message ?: "")
            }
        }
    }

    fun session(action: (Home) -> Unit) {
        viewModelScope.launch {
            val result = sharedPreferences.getString(BuildConfig.APPLICATION_ID, null)
            val session = result?.let { Home.fromJson(it) }
            session?.let { action(it) }
        }
    }

    fun selectHome(home: Home) {
        viewModelScope.launch {
            sharedPreferences
                .edit()
                .putString(BuildConfig.APPLICATION_ID, home.toJson())
                .apply()
        }
    }
}