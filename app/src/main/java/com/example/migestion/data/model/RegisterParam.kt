package com.example.migestion.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterParam(
    val fullName: String,
    val email: String,
    val password: String,
    val avatar: String
)