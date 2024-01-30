package com.example.migestion.data.repositories.invoicerepository

import com.example.migestion.model.Invoice
import com.example.migestion.model.Response
import kotlinx.coroutines.flow.Flow

interface InvoiceRepository {

    suspend fun createInvoice(
        id: Int,
        idAlbarans: List<Int>,
        customer: Int,
        idPaymentMethod: Int,
        date: String,
        finished: Boolean = false
    ): Response<Invoice>

    suspend fun getAllInvoices(): Response<List<Invoice>>

    suspend fun getNextId(): Int
}