package com.example.migestion.data.remote.model

import com.example.migestion.data.db.CustomerEntity
import com.example.migestion.data.db.InvoiceEntity
import com.example.migestion.data.repositories.invoicerepository.InvoiceResponse
import com.example.migestion.model.Customer
import kotlinx.serialization.Serializable

@Serializable
data class ApiCustomer (
    val id: Int,
    val businessName: String,
    val streetAddress: String? = null,
    val city: String? = null,
    val state: String? = null,
    val postalcode: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val cif: String,
    val createdAt: String
)

@Serializable
data class CustomerResponse(
    val data: List<Customer>
)

/*
fun CustomerResponse.map(): List<CustomerEntity> {
    return data.map { apiCustomer ->
        CustomerEntity(
            apiCustomer.id.toLong(),
            apiCustomer.businessName,
            apiCustomer.streetAddress,
            apiCustomer.city,
            apiCustomer.state,
            apiCustomer.postalcode,
            apiCustomer.email,
            apiCustomer.phoneNumber,
            apiCustomer.cif,
            apiCustomer.createdAt
        )
    }
}*/

fun ApiCustomer.map(): CustomerEntity {
    return CustomerEntity(
        id.toLong(),
        businessName,
        streetAddress,
        city,
        state,
        postalcode,
        email,
        phoneNumber,
        cif,
        createdAt
    )
}