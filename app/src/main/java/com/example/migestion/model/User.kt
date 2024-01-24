package com.example.migestion.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    var id: String? = null,
    var name: String? = null,
    var imageUrl: String? = null,
)