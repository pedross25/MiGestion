package com.example.migestion.data.repositories.invoicerepository

import com.example.migestion.data.db.InvoiceEntity
import com.example.migestion.data.model.InvoiceParam
import com.example.migestion.data.network.HttpRoutes
import com.example.migestion.data.remote.model.ApiResponse
import com.example.migestion.model.Invoice
import com.example.migestion.model.Response
import com.example.migestion.model.toInvoice
import com.example.migestion.model.toInvoiceEntity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
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
        paid: Boolean,
        totalPrice: Double,
        finished: Boolean
    ): Response<Invoice> {
        return try {
            val invoice = InvoiceEntity(
                id = id.toLong(),
                customer_id = customer.toLong(),
                date = date,
                payment_method_id = idPaymentMethod.toLong(),
                paid = paid,
                total_price = totalPrice
            )
            invoiceDb.insertInvoice(
                invoice
            )
            //Response.Success(invoice.toInvoice())
            if (finished) {
                val message = httpClient.post(HttpRoutes.Invoice.CREATE) {
                    contentType(ContentType.Application.Json)
                    setBody(
                        InvoiceParam(
                            id = id,
                            idCustomer = customer,
                            idAlbarans = idAlbarans,
                            idPaymentMethod = idPaymentMethod,
                            paid = paid,
                            totalPrice = totalPrice
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
                val message = httpClient.get(HttpRoutes.Invoice.GETALL) {
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
            return 1
        }
    }

}