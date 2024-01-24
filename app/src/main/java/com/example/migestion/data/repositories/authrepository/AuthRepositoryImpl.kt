package com.example.migestion.data.repositories.authrepository

import com.example.migestion.data.model.Login
import com.example.migestion.data.model.RegisterParam
import com.example.migestion.model.Response
import com.example.migestion.model.User
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val httpClient: HttpClient
): AuthRepository  {

    override suspend fun loginUser(email: String, password: String): Flow<Response<User>> {
        return flow {
            try {
                emit(Response.Loading)
                    val user = httpClient.post<User> {
                    url("http://10.0.2.2:8080/auth/login")
                    contentType(ContentType.Application.Json)
                    body = Login(email, password = password)
                }
                emit(Response.Success(user))
            } catch (e: Exception) {
                emit(Response.Failure(e))
            }
        }
    }
    override suspend fun registerUser(email: String, password: String, nombre: String, avatar: String): Flow<Response<String>> {
        return flow {
            try {
                emit(Response.Loading)
                val message = httpClient.post<HttpResponse> { // or your data class
                    url("http://10.0.2.2:8080/auth/register")
                    contentType(ContentType.Application.Json)
                    body = RegisterParam(email = email, password = password, fullName = nombre, avatar = avatar)
                }
                if (message.status == HttpStatusCode.OK) {
                    emit(Response.Success("ok"))
                } else {
                    emit(Response.Failure(Exception(message.content.toString())))
                }
            } catch (e: Exception) {
                emit(Response.Failure(e))
            }
        }
    }
/*
    override suspend fun googleSignIn(credential: AuthCredential): Flow<Response<AuthResult>> {
        TODO("Not yet implemented")
    }*/

}