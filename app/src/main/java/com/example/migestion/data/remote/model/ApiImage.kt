package com.example.migestion.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiImage(
    val id: String,
    val url: String,
    val description: String
)