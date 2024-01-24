package com.example.migestion.data.model

import kotlinx.serialization.Serializable

@Serializable
data class InvoiceParam(
    val idCustomer: Int,
    val idAlbarans: List<Int>,
    val idPaymentMethod: Int
)