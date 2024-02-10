package com.example.migestion.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateProductParams(
    val name: String,
    val price: Double,
    val category: String,
    val amount: Int,
    val description: String?,
    val template: Boolean,
    val invoice: Int? = null,
)