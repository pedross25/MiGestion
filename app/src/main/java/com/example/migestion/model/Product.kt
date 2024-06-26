package com.example.migestion.model

import com.example.migestion.data.db.ProductEntity
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int,
    val name: String,
    val amount: Int,
    val price: Double,
    val category: String,
    val createdAt: String? = null,
    val template: Boolean = false,
    val description: String?,
    val invoice: Int? = null,
    val parentId: Int? = null,
    var images: List<String>? = null
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
    parentId = parent_id?.toInt(),
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
    parent_id = parentId?.toLong(),
    invoice_id = invoice?.toLong()
)
