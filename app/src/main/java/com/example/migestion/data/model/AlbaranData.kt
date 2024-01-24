package com.example.migestion.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AlbaranData(
    val idCustomer: Int,
    val products: List<Int>
)