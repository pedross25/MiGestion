package com.example.migestion.usecases

import android.util.Log
import com.example.migestion.data.repositories.customerrepository.CustomerRepository
import com.example.migestion.data.repositories.invoicerepository.InvoiceRepository
import com.example.migestion.model.Customer
import com.example.migestion.model.Invoice
import com.example.migestion.model.Response

data class InvoiceWithCustomer(
    val customer: Customer,
    val invoice: Invoice
)

class GetInvoiceWithCustomer(
    private val invoiceRepository: InvoiceRepository,
    private val customerRepository: CustomerRepository
) {

    suspend operator fun invoke(): Response<List<InvoiceWithCustomer>> {
        val result: MutableList<InvoiceWithCustomer> = mutableListOf()
        val invoices: List<Invoice> =
            (invoiceRepository.getAllInvoices() as? Response.Success)?.data ?: emptyList()
        if (invoices.isNotEmpty()) {
            for (invoice in invoices) {
                val customer =
                    invoice.customer?.let { customerRepository.getById(invoice.customer) }
                Log.i("INVOICE", "invoice " + invoice.id)
                if (customer is Response.Success) {
                    Log.i("INVOICE", "invoice ENCONTRADA" + invoice.id)
                    result.add(InvoiceWithCustomer(customer.data, invoice))
                }
            }
        }
        return Response.Success(data = result.toList())

    }
}