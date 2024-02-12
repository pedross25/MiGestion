package com.example.migestion.data.remote.model

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val data: T,
    val message: String?,
    val exception: String? = null,
    val statusCode: StatusCode
)

@Serializable
data class StatusCode(
    val value: Int,
    val description: String
)

@Serializable
data class ApiResponse2<T>(
    val data: T? = null,
    val message: String?,
    val exception: String? = null,
    val statusCode: StatusCode
)



sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T) : NetworkResult<T>(data)

    class Error<T>(message: String?, data: T? = null) : NetworkResult<T>(data, message)

    class Loading<T> : NetworkResult<T>()

}

/*
@Serializable
sealed class BaseResponse<T>(
    open val statusCode: StatusCode
) {
    data class SuccessResponse<T>(
        val data: T? = null,
        val message: String? = null,
    ): BaseResponse<T>

    data class ErrorResponse<T>(
        val exception: T? = null,
        val message: String? = null,
    ): BaseResponse<T>(statusCode)
}*/
