package com.example.migestion.data.repositories.productrepository

import com.example.migestion.data.db.ProductEntity

interface ICacheProduct {

    suspend fun getAllProducts(): List<ProductEntity>

    suspend fun insertProduct(
        productEntity: ProductEntity
    )

    suspend fun getProductById(id: Int): ProductEntity?

}