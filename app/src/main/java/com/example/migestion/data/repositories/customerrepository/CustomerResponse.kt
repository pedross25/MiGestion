package com.example.migestion.data.repositories.customerrepository

import com.example.migestion.model.Customer
import kotlinx.serialization.Serializable

@Serializable
data class CustomerResponse(
    val data: List<Customer>
)