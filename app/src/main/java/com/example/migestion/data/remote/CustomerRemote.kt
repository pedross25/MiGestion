package com.example.migestion.data.remote

import com.example.migestion.data.db.CustomerEntity
import com.example.migestion.data.model.CustomerParam
import com.example.migestion.data.remote.model.ApiCustomer
import com.example.pruebacom.data.remote.model.ApiResponse
import com.example.migestion.data.remote.model.CustomerResponse
import com.example.migestion.data.remote.model.map
import com.example.migestion.data.repositories.customerrepository.IRemoteCustomer
import com.example.migestion.data.network.HttpRoutes
import com.example.migestion.model.Customer
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.RedirectResponseException
import io.ktor.client.features.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.url
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
            val message = httpClient.post<ApiResponse<ApiCustomer>> {
                url(HttpRoutes.Customer.CREATE)
                contentType(ContentType.Application.Json)
                body = CustomerParam(
                    businessName = name,
                    streetAddress = streetAddress,
                    city = city,
                    state = state,
                    postalCode = postalCode,
                    email = email,
                    phoneNumber = phoneNumber,
                    cif = cif
                )
            }
            message.data.map()
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

    override suspend fun getAll(): List<Customer>

         = httpClient.get<CustomerResponse>(HttpRoutes.Customer.GETALL).data



}

