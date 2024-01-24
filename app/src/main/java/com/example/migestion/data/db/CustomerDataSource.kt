package com.example.migestion.data.db


interface CustomerDataSource {

    suspend fun getCustomerList(): List<CustomerEntity>

    suspend fun insertCustomer(
        id: Int,
        businessName: String,
        streetAddress: String? = null,
        city: String? = null,
        state: String? = null,
        postalcode: String? = null,
        email: String? = null,
        phoneNumber: String? = null,
        cif: String,
        createdAt: String
    )
}