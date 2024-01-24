package com.example.migestion.data.model

import com.example.migestion.model.Customer
import com.example.migestion.model.Invoice


data class FullInvoice(
    val invoice: Invoice,
    val customer: Customer,
    val totalCost: Double
)