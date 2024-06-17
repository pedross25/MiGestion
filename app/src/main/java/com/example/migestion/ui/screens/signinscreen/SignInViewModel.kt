package com.example.migestion.ui.screens.signinscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.migestion.data.repositories.authrepository.AuthRepository
import com.example.migestion.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    val _signInState = Channel<SignInState>()
    val signInState = _signInState.receiveAsFlow()

    fun loginUser(email: String, password: String) = viewModelScope.launch {
        repository.loginUser(email, password).collect { result ->
            when (result) {
                is Response.Success -> {
                    _signInState.send(SignInState(isSuccess = "Sign In Success"))
                }

                is Response.Loading -> {
                    _signInState.send(SignInState(isLoading = true))
                }

                is Response.Failure -> {
                    _signInState.send(SignInState(isError = result.e?.message))
                }
            }
        }
    }
}