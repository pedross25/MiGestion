package com.example.migestion.data.remote

import com.example.migestion.data.model.InvoiceParam
import com.example.migestion.data.repositories.invoicerepository.IRemoteInvoice
import com.example.migestion.data.repositories.invoicerepository.InvoiceResponse
import com.example.migestion.model.Invoice
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class InvoiceRemote @Inject constructor(
    private val httpClient: HttpClient,
) : IRemoteInvoice {
    override suspend fun createInvoice(invoiceParam: InvoiceParam): Invoice? {
        val message = httpClient.post<Invoice> {
            url("http://10.0.2.2:8080/invoice/create")
            contentType(ContentType.Application.Json)
            body = invoiceParam
        }
        return message
    }

    override suspend fun getAll(): List<Invoice> {
        val message = httpClient.get<InvoiceResponse> {
            url("http://10.0.2.2:8080/invoice/getAll")
            contentType(ContentType.Application.Json)
        }
        return message.data
    }
}