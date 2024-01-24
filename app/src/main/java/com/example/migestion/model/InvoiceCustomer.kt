package com.example.migestion.model

import kotlinx.serialization.Serializable

@Serializable
data class InvoiceCustomer(
    val invoice: Invoice,
    val customer: Customer
)