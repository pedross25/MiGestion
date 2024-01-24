package com.example.migestion.data.model

import com.example.migestion.data.db.ProductEntity

data class AlbaranWithProducts(
    val id: Long,
    val customer_id: Long?,
    val fecha: String,
    val products: List<ProductEntity>
)