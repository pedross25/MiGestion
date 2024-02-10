package com.example.migestion.data.repositories.invoicerepository

import android.util.Log
import com.example.migestion.data.cache.InvoiceCache
import com.example.migestion.data.db.CustomerEntity
import com.example.migestion.data.db.InvoiceEntity
import com.example.migestion.data.model.InvoiceParam
import com.example.migestion.data.remote.model.ApiResponse
import com.example.migestion.data.repositories.customerrepository.CustomerRepository
import com.example.migestion.data.repositories.customerrepository.ICacheCustomer
import com.example.migestion.data.repositories.customerrepository.IRemoteCustomer
import com.example.migestion.model.Invoice
import com.example.migestion.model.InvoiceCustomer
import com.example.migestion.model.Response
import com.example.migestion.model.toInvoice
import com.example.migestion.model.toInvoiceEntity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
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
        id: Int,
        idAlbarans: List<Int>,
        customer: Int,
        idPaymentMethod: Int,
        date: String,
        finished: Boolean
    ): Response<Invoice> {
        return try {
            val invoice = InvoiceEntity(
                id = id.toLong(),
                customer_id = customer.toLong(),
                date = date,
                payment_method_id = idPaymentMethod.toLong()
            )
            invoiceDb.insertInvoice(
                invoice
            )
            //Response.Success(invoice.toInvoice())
            if (finished) {
                val message = httpClient.post("http://10.0.2.2:8080/invoice/create") {
                    contentType(ContentType.Application.Json)
                    setBody(
                        InvoiceParam(
                            id = id,
                            idCustomer = customer,
                            idAlbarans = idAlbarans,
                            idPaymentMethod = idPaymentMethod
                        )
                    )
                }
            }
            Response.Success(invoice.toInvoice())

        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun getAllInvoices(): Response<List<Invoice>> {
        return try {
             val invoices = invoiceDb.getAllInvoice()
            if (invoices.isNotEmpty()) {
                Response.Success(invoices.map { it.toInvoice() })
            } else {
                val message = httpClient.get("http://10.0.2.2:8080/invoice/getAll") {
                    contentType(ContentType.Application.Json)
                }
                withContext(Dispatchers.IO) {
                    message.body<ApiResponse<List<Invoice>>>().data.forEach {
                        invoiceDb.insertInvoice(
                            it.toInvoiceEntity()
                        )
                    }
                }
                Response.Success(data = message.body<ApiResponse<List<Invoice>>>().data)
            }
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun getNextId(): Int  {
        return try {
            invoiceDb.getNextNum() + 1
        } catch (e: Exception) {
            val next = getAllInvoices()
            if (next is Response.Success)
                return getNextId()
            else
                return 0
        }
    }

}