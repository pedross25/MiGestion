package com.example.migestion.data.cache

import com.example.migestion.data.db.CustomerEntity
import com.example.migestion.data.db.Database
import com.example.migestion.data.repositories.customerrepository.ICacheCustomer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CustomerCache @Inject constructor(
    db: Database
) : ICacheCustomer {
    private val queries = db.customerQueries
    override suspend fun getCustomerList(): List<CustomerEntity> {
        return withContext(Dispatchers.IO) {
            queries.selectAll().executeAsList()
        }
    }

    override suspend fun insertCustomer(customerEntity: CustomerEntity) {
        queries.insert(
            customerEntity.id,
            customerEntity.businessName,
            customerEntity.streetAddress,
            customerEntity.city,
            customerEntity.state,
            customerEntity.postalCode,
            customerEntity.email,
            customerEntity.phoneNumber,
            customerEntity.cif,
            customerEntity.createdAt
        )
    }

    override suspend fun getCustomerById(id: Int): CustomerEntity? {
        return withContext(Dispatchers.IO) {
            queries.selectById(id.toLong()).executeAsOneOrNull()
        }
    }
}