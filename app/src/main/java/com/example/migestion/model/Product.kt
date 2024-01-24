package com.example.migestion.model

import com.example.migestion.data.db.ProductEntity

data class Product(
    val id: Int,
    val name: String,
    val amount: Int,
    val price: Double,
    val category: String,
    val createdAt: String?,
    val template: Boolean?,
    val description: String?,
    val invoice: Int?
)


fun ProductEntity.toProduct() = Product(
    id = id.toInt(),
    name = name,
    amount = amount.toInt(),
    price = price,
    category = category,
    createdAt = created_at,
    template = template,
    description = description,
    invoice = invoice_id?.toInt()
)

fun Product.toProductEntity() = ProductEntity(
    id = id.toLong(),
    name = name,
    amount = amount.toLong(),
    price = price,
    category = category,
    created_at = createdAt,
    template = template,
    description = description,
    invoice_id = invoice?.toLong()
)
