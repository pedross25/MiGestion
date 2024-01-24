package com.example.migestion.data.repositories.invoicerepository

import com.example.migestion.model.Invoice
import kotlinx.serialization.Serializable

@Serializable
data class InvoiceResponse(
    val data: List<Invoice>
)