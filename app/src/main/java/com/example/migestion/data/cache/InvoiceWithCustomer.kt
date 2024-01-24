package com.example.migestion.data.cache

import com.example.migestion.data.db.Database
import com.example.migestion.data.db.SelectInvoiceWithCustomer
import javax.inject.Inject

class InvoiceWithCustomer @Inject constructor(
    val db: Database
) {
    private val queries = db.invoiceWithCustomerQueries

    fun getInvoiceWithProducts(): List<SelectInvoiceWithCustomer> {
        return queries.selectInvoiceWithCustomer().executeAsList()
    }
}