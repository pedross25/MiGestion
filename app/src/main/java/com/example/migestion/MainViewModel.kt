package com.example.migestion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.migestion.data.Session
import com.example.migestion.ui.navigation.graphs.Graph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val session: Session
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading get() = _isLoading.asStateFlow()

    private val _startDestination = MutableStateFlow<String?>(null)
    val startDestination get() = _startDestination.asStateFlow()

    init {
        viewModelScope.launch {
            session.isUserLoggedIn().collect { isLogued ->
                _startDestination.value = if (isLogued) {
                    _isLoading.value = false
                    Graph.HOME
                } else {
                    _isLoading.value = false
                    Graph.AUTHENTICATION
                }

            }
        }
    }


}
