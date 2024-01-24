package com.example.migestion.data.remote.model.mapper

import com.example.migestion.data.remote.model.ApiCustomer
import com.example.migestion.model.Customer

class ApiCustomerMapper {

    fun map(data: ApiCustomer): Customer = data.run {
        Customer(
            id,
            businessName,
            streetAddress,
            city,
            state,
            postalcode,
            email,
            phoneNumber,
            cif,
            createdAt
        )
    }
}