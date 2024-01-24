package com.example.migestion.data.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CustomerDataSourceImpl (
    db: Database
): CustomerDataSource {

    private val queries = db.customerQueries
    override suspend fun getCustomerList(): List<CustomerEntity> {
        return withContext(Dispatchers.IO) {
            queries.selectAll().executeAsList()
        }
    }

    override suspend fun insertCustomer(
        id: Int,
        businessName: String,
        streetAddress: String?,
        city: String?,
        state: String?,
        postalcode: String?,
        email: String?,
        phoneNumber: String?,
        cif: String,
        createdAt: String
    ) {
        queries.insert(id.toLong(), businessName, streetAddress, city, state, postalcode, email, phoneNumber, cif, createdAt)
    }


}