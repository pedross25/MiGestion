package com.example.migestion.data.model

import kotlinx.serialization.Serializable

@Serializable
data class InvoiceParam(
    val id: Int,
    val idCustomer: Int,
    val idAlbarans: List<Int>,
    val idPaymentMethod: Int,
    val paid: Boolean,
    val totalPrice: Double
)