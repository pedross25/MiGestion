package com.example.migestion.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Login(
    val email: String,
    val password: String
)