package com.example.migestion.model

import com.example.migestion.data.db.InvoiceEntity
import kotlinx.serialization.Serializable

@Serializable
data class Invoice(
    val id: Int,
    val createdAt: String?,
    val paymentMethod: Int?,
    val customer: Int?
)

fun InvoiceEntity.toInvoice(): Invoice = Invoice(
    id = id.toInt(),
    createdAt = date,
    paymentMethod = payment_method_id?.toInt(),
    customer = customer_id?.toInt()
)