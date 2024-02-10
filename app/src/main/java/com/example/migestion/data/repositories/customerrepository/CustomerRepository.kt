package com.example.migestion.data.repositories.customerrepository

import com.example.migestion.data.db.CustomerEntity
import com.example.migestion.model.Customer
import com.example.migestion.model.Response
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {

    suspend fun createCustomer(
        name: String,
        streetAddress: String,
        city: String,
        state: String,
        postalCode: String,
        email: String,
        phoneNumber: String,
        cif: String
    ): Flow<Response<Customer>>

    suspend fun getAll(): Response<List<Customer>>

    suspend fun getById(id: Int): Response<Customer>

}