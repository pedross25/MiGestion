package com.example.migestion.ui.screens.signupscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.migestion.data.repositories.authrepository.AuthRepository
import com.example.migestion.model.Response
import com.example.migestion.ui.screens.signinscreen.SignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    val _signUnState = Channel<SignInState>()
    val signUnState = _signUnState.receiveAsFlow()

    fun signUp(email: String, password: String, nombre: String, avatar: String) =
        viewModelScope.launch {
            repository.registerUser(email, password, nombre, avatar).collect { result ->
                when (result) {
                    is Response.Failure -> _signUnState.send(SignInState(isError = result.e?.message))
                    is Response.Loading -> _signUnState.send(SignInState(isLoading = true))
                    is Response.Success -> _signUnState.send(SignInState(isSuccess = "Sign Up Success"))
                }
            }
        }


}