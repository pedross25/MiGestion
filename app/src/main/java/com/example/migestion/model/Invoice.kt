package com.example.migestion.model

import com.example.migestion.data.db.InvoiceEntity
import kotlinx.serialization.Serializable

@Serializable
data class Invoice(
    val id: Int,
    val createdAt: String?,
    val paymentMethod: Int?,
    val customer: Int?,
    val totalPrice: Double,
    val paid: Boolean
)

fun InvoiceEntity.toInvoice(): Invoice = Invoice(
    id = id.toInt(),
    createdAt = date,
    paymentMethod = payment_method_id?.toInt(),
    customer = customer_id?.toInt(),
    totalPrice = total_price,
    paid = paid
)

fun Invoice.toInvoiceEntity(): InvoiceEntity = InvoiceEntity(
    id = id.toLong(),
    date = createdAt,
    payment_method_id = paymentMethod?.toLong(),
    customer_id = customer?.toLong(),
    total_price = totalPrice,
    paid = paid
)