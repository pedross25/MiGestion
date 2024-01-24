package com.example.migestion.data.repositories.customerrepository

import com.example.migestion.data.db.CustomerEntity


interface ICacheCustomer {
    suspend fun getCustomerList(): List<CustomerEntity>

    suspend fun insertCustomer(
        customerEntity: CustomerEntity
    )

    suspend fun getCustomerById(id: Int): CustomerEntity?
}