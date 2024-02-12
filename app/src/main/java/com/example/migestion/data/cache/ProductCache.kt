package com.example.migestion.data.cache

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.migestion.data.db.Database
import com.example.migestion.data.db.ProductEntity
import com.example.migestion.data.repositories.productrepository.ICacheProduct
import com.example.migestion.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductCache @Inject constructor(
    private val db: Database
) : ICacheProduct {
    private val queries = db.productQueries
    override suspend fun getAllProducts(): List<ProductEntity> {
        return withContext(Dispatchers.IO) {
            queries.selectAll().executeAsList()
        }
    }

    override suspend fun insertProduct(productEntity: ProductEntity) {
        withContext(Dispatchers.IO) {
            queries.insert(
                name = productEntity.name,
                amount = productEntity.amount,
                price = productEntity.price,
                category = productEntity.category,
                template = productEntity.template,
                description = productEntity.description,
                invoice_id = productEntity.invoice_id,
                id = productEntity.id,
                parent_id = productEntity.parent_id
            )
        }
    }

    override suspend fun getProductById(id: Int): ProductEntity? {
        //TODO
        return null
    }

    override suspend fun getProductsFromInvoice(id: Int): List<ProductEntity> {
        return withContext(Dispatchers.IO) {
            queries.selectByIdInvoice(id.toLong()).executeAsList()
        }
    }

    override suspend fun getTemplateProducts(): List<ProductEntity> {
        return withContext(Dispatchers.IO) {
            queries.selectTemplateProducts().executeAsList()
        }
    }

    override suspend fun getFlowProductsByInvoice(idInvoice: Int): Flow<List<ProductEntity>> {
        return queries.selectByIdInvoice(idInvoice.toLong()).asFlow().mapToList(Dispatchers.IO)
    }

    override suspend fun getNextId(): Int {
        return (queries.getMaxId().executeAsOne().MAX?.toInt()?.plus(1)) ?: 0
    }
}