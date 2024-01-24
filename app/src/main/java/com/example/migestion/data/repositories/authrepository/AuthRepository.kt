package com.example.migestion.data.repositories.authrepository

import com.example.migestion.model.Response
import com.example.migestion.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun loginUser(email: String, password: String): Flow<Response<User>>

    suspend fun registerUser(email: String, password: String, nombre: String, avatar: String): Flow<Response<String>>

}