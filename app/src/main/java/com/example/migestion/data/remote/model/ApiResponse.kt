package com.example.pruebacom.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val data: T,
    val message: String?,
    val statusCode: StatusCode
)

@Serializable
data class StatusCode(
    val value: Int,
    val description: String
)