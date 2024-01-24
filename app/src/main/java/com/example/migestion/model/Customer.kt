package com.example.migestion.model

import com.example.migestion.data.db.CustomerEntity
import kotlinx.serialization.Serializable

@Serializable
data class Customer(
    val id: Int,
    val businessName: String,
    val streetAddress: String? = null,
    val city: String? = null,
    val state: String? = null,
    val postalCode: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val cif: String,
    val createdAt: String
)

fun CustomerEntity.toCustomer() = Customer(
    id = id.toInt(),
    businessName = businessName,
    streetAddress = streetAddress,
    city = city,
    state = state,
    postalCode = postalCode,
    email = email,
    phoneNumber = phoneNumber,
    cif = cif,
    createdAt = createdAt
)