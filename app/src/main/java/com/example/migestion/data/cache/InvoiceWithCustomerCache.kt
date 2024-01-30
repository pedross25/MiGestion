package com.example.migestion.data.cache

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.migestion.data.db.Database
import com.example.migestion.data.db.SelectInvoiceWithCustomer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InvoiceWithCustomerCache @Inject constructor(
    val db: Database
) {
    private val queries = db.invoiceWithCustomerQueries

    fun getInvoiceWithProducts(): Flow<List<SelectInvoiceWithCustomer>> {
        return queries.selectInvoiceWithCustomer().asFlow().mapToList(Dispatchers.IO)
    }

}