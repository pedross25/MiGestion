package com.example.migestion.data.repositories.authrepository

import com.example.migestion.data.model.Login
import com.example.migestion.data.model.RegisterParam
import com.example.migestion.data.remote.model.ApiResponse
import com.example.migestion.model.Response
import com.example.migestion.model.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val httpClient: HttpClient
) : AuthRepository {

    override suspend fun loginUser(email: String, password: String): Flow<Response<User>> {
        return flow {
            try {
                emit(Response.Loading)
                val response = httpClient.post("http://5.250.187.56:8080/auth/login") {
                    contentType(ContentType.Application.Json)
                    setBody(Login(email, password = password))
                }
                val user = response.body<ApiResponse<User>>()
                emit(Response.Success(user.data))
            } catch (e: Exception) {
                emit(Response.Failure(e))
            }
        }
    }

    @OptIn(InternalAPI::class)
    override suspend fun registerUser(
        email: String,
        password: String,
        nombre: String,
        avatar: String
    ): Flow<Response<String>> {
        return flow {
            try {
                emit(Response.Loading)
                val message =
                    httpClient.post("http://5.250.187.56:8080/auth/register") { // or your data class
                        contentType(ContentType.Application.Json)
                        setBody(
                            RegisterParam(
                                email = email,
                                password = password,
                                fullName = nombre,
                                avatar = avatar
                            )
                        )

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

}