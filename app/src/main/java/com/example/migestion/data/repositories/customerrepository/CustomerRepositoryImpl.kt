package com.example.migestion.data.repositories.customerrepository

import android.util.Log
import com.example.migestion.data.db.CustomerEntity
import com.example.migestion.model.Customer
import com.example.migestion.model.Response
import com.example.migestion.model.toCustomer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CustomerRepositoryImpl @Inject constructor(
    private val customerDb: ICacheCustomer,
    private val customerApi: IRemoteCustomer
) : CustomerRepository {
    override suspend fun createCustomer(
        name: String,
        streetAddress: String,
        city: String,
        state: String,
        postalCode: String,
        email: String,
        phoneNumber: String,
        cif: String
    ): Flow<Response<Customer>> {
        return flow {
            try {
                emit(Response.Loading)

                val customer = customerApi.createCustomer(
                    name = name,
                    streetAddress = streetAddress,
                    city = city,
                    state = state,
                    postalCode = postalCode,
                    email = email,
                    phoneNumber = phoneNumber,
                    cif = cif
                )

                if (customer != null) {
                    withContext(Dispatchers.IO) {
                        customerDb.insertCustomer(customer)
                    }
                    emit(Response.Success(customer.toCustomer()))
                }
            } catch (e: Exception) {
                Log.i("GESTION", e.toString())
                emit(Response.Failure(e))
            }
        }
    }

    override suspend fun getAll(): Response<List<Customer>> {
            return try {
                val customers = customerDb.getCustomerList()
                if (customers.isNotEmpty()) {
                    Response.Success(data = customers.map { it.toCustomer() })
                } else {
                    val apiResponse = customerApi.getAll()
                    withContext(Dispatchers.IO) {
                        apiResponse.forEach {
                            customerDb.insertCustomer(
                                CustomerEntity(
                                    it.id.toLong(),
                                    it.businessName,
                                    it.streetAddress,
                                    it.city,
                                    it.state,
                                    it.postalcode,
                                    it.email,
                                    it.phoneNumber,
                                    it.cif,
                                    it.createdAt
                                )
                            )
                        }
                    }

                    Response.Success(data = apiResponse)
                }

            } catch (e: Exception) {
                Response.Failure(e)
            }
        }


    override suspend fun getById(id: Int): Response<Customer> {
        try {
            val customer = customerDb.getCustomerById(id)

            return if (customer != null) {
                Response.Success(data = customer.toCustomer())
            } else {
                val getAllResponse = getAll()

                if (getAllResponse is Response.Success) {
                    val foundCustomer = getAllResponse.data.firstOrNull { it.id == id }

                    foundCustomer?.let {
                        Response.Success(data = it)
                    } ?: Response.Failure(Exception("Customer not found"))
                } else {
                    Response.Failure(Exception("Customer not found"))
                }
            }
        } catch (e: Exception) {
            return Response.Failure(e)
        }
    }

}