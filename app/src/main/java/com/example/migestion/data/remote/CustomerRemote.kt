package com.example.migestion.data.remote

import com.example.migestion.data.db.CustomerEntity
import com.example.migestion.data.model.CustomerParam
import com.example.migestion.data.network.HttpRoutes
import com.example.migestion.data.remote.model.ApiCustomer
import com.example.migestion.data.remote.model.ApiResponse
import com.example.migestion.data.remote.model.map
import com.example.migestion.data.repositories.customerrepository.IRemoteCustomer
import com.example.migestion.model.Customer
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class CustomerRemote @Inject constructor(
    private val httpClient: HttpClient,
) : IRemoteCustomer {
    override suspend fun createCustomer(
        name: String,
        streetAddress: String,
        city: String,
        state: String,
        postalCode: String,
        email: String,
        phoneNumber: String,
        cif: String
    ): CustomerEntity? {

        return try {
            val message = httpClient.post(HttpRoutes.Customer.CREATE) {
                contentType(ContentType.Application.Json)
                setBody(CustomerParam(
                    businessName = name,
                    streetAddress = streetAddress,
                    city = city,
                    state = state,
                    postalCode = postalCode,
                    email = email,
                    phoneNumber = phoneNumber,
                    cif = cif
                ))
            }
            message.body<ApiResponse<ApiCustomer>>().data.map()
        } catch (e: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${e.response.status.description}")
            null
        } catch (e: ClientRequestException) {
            // 4xx - responses
            println("Error: ${e.response.status.description}")
            null
        } catch (e: ServerResponseException) {
            // 5xx - responses
            println("Error: ${e.response.status.description}")
            null
        } catch (e: Exception) {
            println("Error: ${e.message}")
            null
        }
    }

    override suspend fun getAll(): List<Customer> {
        try {
            val message = httpClient.get(HttpRoutes.Customer.GETALL)
            return message.body<ApiResponse<List<Customer>>>().data
        } catch (e: Exception) {
            return emptyList()
        }

    }


}

