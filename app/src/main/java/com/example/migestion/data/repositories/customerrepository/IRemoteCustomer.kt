package com.example.migestion.data.repositories.customerrepository

import android.content.Context
import com.example.migestion.data.db.CustomerEntity
import com.example.migestion.model.Customer

interface IRemoteCustomer {

    suspend fun createCustomer(
        name: String,
        streetAddress: String,
        city: String,
        state: String,
        postalCode: String,
        email: String,
        phoneNumber: String,
        cif: String
    ): CustomerEntity?

    suspend fun getAll(): List<Customer>

}