package com.example.migestion.ui.screens.signupscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.migestion.data.repositories.authrepository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {


    fun signUp(email: String, password: String, nombre: String, avatar: String) =
        viewModelScope.launch {
            repository.registerUser(email, password, nombre, avatar).collect()
        }





}