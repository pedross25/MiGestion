package com.example.migestion.data.repositories.invoicerepository

import com.example.migestion.data.db.InvoiceEntity
import com.example.migestion.model.Invoice

interface IRemoteInvoice {

    suspend fun createInvoice(

    ): InvoiceEntity?

    suspend fun getAll(): List<Invoice>

}