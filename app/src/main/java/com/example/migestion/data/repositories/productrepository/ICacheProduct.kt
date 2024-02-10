package com.example.migestion.data.repositories.productrepository

import com.example.migestion.data.db.ProductEntity
import com.example.migestion.model.Product
import kotlinx.coroutines.flow.Flow

interface ICacheProduct {

    suspend fun getAllProducts(): List<ProductEntity>

    suspend fun insertProduct(
        productEntity: ProductEntity
    )

    suspend fun getProductById(id: Int): ProductEntity?

    suspend fun getProductsFromInvoice(id: Int): List<ProductEntity>

    suspend fun getTemplateProducts(): List<ProductEntity>

    suspend fun getFlowProductsByInvoice(idInvoice: Int): Flow<List<ProductEntity>>

    suspend fun getNextId(): Int

}