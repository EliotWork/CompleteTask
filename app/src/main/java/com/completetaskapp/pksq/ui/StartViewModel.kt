package com.completetaskapp.pksq.ui

import androidx.lifecycle.ViewModel
import com.completetaskapp.pksq.data.models.ConfigSettings
import com.completetaskapp.pksq.data.repository.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
class StartViewModel(private val repository: Repository) : ViewModel() {

    private val _launchedIntentState = MutableStateFlow(false)
    private val _path = MutableStateFlow("")
    private val _fetchSettings = repository.configSettings
    private val _link = repository.link

    val fetchSettings: Flow<Repository.Resource<ConfigSettings?>> get() = _fetchSettings
    val link: Flow<String> get() = _link.transform {
        _path.value = it
        emit(it)
    }
    val path get() = _path.value

    fun setLaunchedIntentState(state: Boolean) {
        _launchedIntentState.value = state
    }
}