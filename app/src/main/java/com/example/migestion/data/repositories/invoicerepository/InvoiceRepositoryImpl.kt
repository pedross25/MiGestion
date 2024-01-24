package com.example.migestion.data.repositories.invoicerepository

import android.util.Log
import com.example.migestion.data.cache.InvoiceCache
import com.example.migestion.data.db.CustomerEntity
import com.example.migestion.data.db.InvoiceEntity
import com.example.migestion.data.model.InvoiceParam
import com.example.migestion.data.repositories.customerrepository.CustomerRepository
import com.example.migestion.data.repositories.customerrepository.ICacheCustomer
import com.example.migestion.data.repositories.customerrepository.IRemoteCustomer
import com.example.migestion.model.Invoice
import com.example.migestion.model.InvoiceCustomer
import com.example.migestion.model.Response
import com.example.migestion.model.toInvoice
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InvoiceRepositoryImpl @Inject constructor(
    //private val invoiceApi: IRemoteInvoice,
    private val httpClient: HttpClient,
    private val invoiceDb: ICacheInvoice
) : InvoiceRepository {


    // TODO Quitar el Response ??
    override suspend fun createInvoice(
        idAlbarans: List<Int>,
        customer: Int,
        idPaymentMethod: Int
    ): Response<Invoice> {
        return try {
            val message = httpClient.post<Invoice> {
                url("http://10.0.2.2:8080/invoice/create")
                contentType(ContentType.Application.Json)
                body = InvoiceParam(
                    idCustomer = customer,
                    idAlbarans = idAlbarans,
                    idPaymentMethod = idPaymentMethod
                )
            }
            Response.Success(message)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun getAllInvoices(): Response<List<Invoice>> {
        return try {
            val invoices = invoiceDb.getAllInvoice()
            if (invoices.isNotEmpty()) {
                Response.Success(invoices.map { it.toInvoice() })
            }
            else {
                val message = httpClient.get<InvoiceResponse> {
                    url("http://10.0.2.2:8080/invoice/getAll")
                    contentType(ContentType.Application.Json)
                }
                withContext(Dispatchers.IO) {
                    message.data.forEach {
                        invoiceDb.insertInvoice(
                            invoiceEntity = InvoiceEntity(
                                id = it.id.toLong(),
                                date = it.createdAt,
                                payment_method_id = it.paymentMethod?.toLong(),
                                customer_id = it.customer?.toLong()
                            )
                        )
                    }
                }
                Response.Success(data = message.data)
            }
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun getNextId(): Int = invoiceDb.getNextNum() + 1

}