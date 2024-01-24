package com.example.migestion.data.cache

import com.example.migestion.data.db.Database
import com.example.migestion.data.db.InvoiceEntity
import com.example.migestion.data.repositories.invoicerepository.ICacheInvoice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InvoiceCache @Inject constructor(
    db: Database
): ICacheInvoice {

    private val queries = db.invoiceQueries
    override suspend fun getAllInvoice(): List<InvoiceEntity> {
        return withContext(Dispatchers.IO) {
            queries.selectAll().executeAsList()
        }
    }

    override suspend fun insertInvoice(invoiceEntity: InvoiceEntity) {
        withContext(Dispatchers.IO) {
            queries.insert(
                invoiceEntity.id,
                invoiceEntity.date,
                invoiceEntity.payment_method_id,
                invoiceEntity.customer_id
            )
        }
    }

    override suspend fun getInvoiceById(id: Int): InvoiceEntity? {
        return withContext(Dispatchers.IO) {
            queries.selectById(id.toLong()).executeAsOneOrNull()
        }
    }

    override suspend fun getNextNum(): Int {
        return queries.getMaxId().executeAsOne().MAX!!.toInt()
    }
}