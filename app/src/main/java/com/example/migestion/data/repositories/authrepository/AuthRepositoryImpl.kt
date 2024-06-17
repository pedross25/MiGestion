package com.example.migestion.data.repositories.authrepository

import com.example.migestion.data.Session
import com.example.migestion.data.model.CreateUserParams
import com.example.migestion.data.model.Login
import com.example.migestion.data.network.HttpRoutes
import com.example.migestion.data.remote.model.ApiResponse2
import com.example.migestion.model.Response
import com.example.migestion.model.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val httpClient: HttpClient,
    private val session: Session
) : AuthRepository {

    override suspend fun loginUser(email: String, password: String): Flow<Response<User>> {
        return flow {
            try {
                emit(Response.Loading)
                val response = httpClient.post(HttpRoutes.Auth.LOGIN) {
                    contentType(ContentType.Application.Json)
                    setBody(Login(email, password = password))
                }
                if (response.status.isSuccess()) {
                    val res = response.body<ApiResponse2<User>>()
                    res.data?.let {
                        emit(Response.Success(data = res.data))
                    }
                    session.setUserLoggedIn(true)
                } else {
                    val res = response.body<ApiResponse2<String>>()
                    res.exception?.let {
                        emit(Response.Failure(Exception(res.exception)))
                    }
                }
            } catch (e: Exception) {
                emit(Response.Failure(e))
            }
        }
    }

    override suspend fun registerUser(
        email: String,
        password: String,
        nombre: String,
        avatar: String
    ): Flow<Response<String>> {
        return flow {
            try {
                emit(Response.Loading)
                val route = HttpRoutes.Auth.REGISTER
                val response =
                    httpClient.post(route) {
                        contentType(ContentType.Application.Json)
                        setBody(
                            CreateUserParams(
                                email = email,
                                password = password,
                                fullName = nombre,
                                avatar = avatar
                            )
                        )
                    }
                if (response.status.isSuccess()) {
                    emit(Response.Success(data = response.body<String>()))
                } else {
                    val res = response.body<ApiResponse2<String>>()
                    emit(Response.Failure(Exception(res.exception)))
//                    res.exception?.let {
//                        emit(Response.Failure(Exception(res.exception)))
//                    }
                }
            } catch (e: Exception) {
                emit(Response.Failure(e))
            }
        }
    }

    override suspend fun isLoggin(): Boolean {
        val userLoggedInFlow = session.isUserLoggedIn()
        val isUserLoggedIn = userLoggedInFlow.first()
        return isUserLoggedIn
    }

}