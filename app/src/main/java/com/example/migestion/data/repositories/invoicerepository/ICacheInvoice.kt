package com.example.migestion.data.repositories.invoicerepository

import com.example.migestion.data.db.InvoiceEntity

interface ICacheInvoice {
    suspend fun getAllInvoice(): List<InvoiceEntity>

    suspend fun insertInvoice(
        invoiceEntity: InvoiceEntity
    )

    suspend fun getInvoiceById(id: Int): InvoiceEntity?

    suspend fun getNextNum(): Int
}