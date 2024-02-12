package com.example.migestion.data.repositories.authrepository

import com.example.migestion.data.Session
import com.example.migestion.data.model.Login
import com.example.migestion.data.model.RegisterParam
import com.example.migestion.data.network.HttpRoutes
import com.example.migestion.data.remote.model.ApiResponse2
import com.example.migestion.model.Response
import com.example.migestion.model.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.util.InternalAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val httpClient: HttpClient,
    private val session: Session
) : AuthRepository {

     /*suspend fun <T : Any> makePostRequest(url: String, body: Any): Flow<Response<T>> {
        return flow {
            try {
                emit(Response.Loading)
                val response = httpClient.post<HttpResponse>(url) {
                    contentType(ContentType.Application.Json)
                    setBody(body)
                }

                if (response.status.isSuccess()) {
                    val responseData = response.body<ApiResponse2<T>>()
                    responseData.data?.let {
                        emit(Response.Success(data = responseData.data))
                    } ?: run {
                        emit(Response.Failure(Exception("Empty response data")))
                    }
                } else {
                    val errorData = response.body<ApiResponse2<String>>()
                    val errorMessage = errorData.exception ?: "Unknown error"
                    emit(Response.Failure(Exception(errorMessage)))
                }
            } catch (e: Exception) {
                emit(Response.Failure(e))
            }
        }
    }*/

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
                    httpClient.post(HttpRoutes.Auth.REGISTER) {
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

    override suspend fun isLoggin(): Boolean {
        // Llama al método isUserLoggedIn() para obtener el flujo de estado de inicio de sesión
        val userLoggedInFlow = session.isUserLoggedIn()

        // Recolecta el valor del flujo para determinar si el usuario está conectado o no
        val isUserLoggedIn = userLoggedInFlow.first()

        // Devuelve el valor obtenido del flujo
        return isUserLoggedIn
    }

}