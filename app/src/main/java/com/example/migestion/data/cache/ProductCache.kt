package com.example.migestion.data.cache

import com.example.migestion.data.db.Database
import com.example.migestion.data.db.ProductEntity
import com.example.migestion.data.repositories.productrepository.ICacheProduct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductCache @Inject constructor(
    private val db: Database
): ICacheProduct {
    private val queries = db.productQueries
    override suspend fun getAllProducts(): List<ProductEntity> {
        return withContext(Dispatchers.IO) {
            queries.selectAll().executeAsList()
        }
    }

    override suspend fun insertProduct(productEntity: ProductEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun getProductById(id: Int): ProductEntity? {
        TODO("Not yet implemented")
    }
}