package com.example.migestion.ui.screens.invoicescreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.migestion.data.cache.InvoiceWithCustomerCache
import com.example.migestion.data.db.SelectInvoiceWithCustomer
import com.example.migestion.model.Response
import com.example.migestion.usecases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvoiceViewModel @Inject constructor(
    private val useCases: UseCases,
    private val invoiceWithCustomer: InvoiceWithCustomerCache
) : ViewModel() {

    var invoiceResponse by mutableStateOf<Response<List<SelectInvoiceWithCustomer>>>(Response.Loading)
        private set

    init {
        viewModelScope.launch {
            useCases.getInvoiceWithCustomer.invoke()
        }
        subscribe()
    }

    private fun subscribe() = viewModelScope.launch {
        invoiceWithCustomer.getInvoiceWithProducts().collect{
            val response = it.sortedByDescending { it.invoice_id }
            invoiceResponse = Response.Success(data = response)
            Log.i("MIGESTION_INVOICE_WITH_PRODUCT", response.size.toString())
        }
    }


    /*private fun getInvoices() = viewModelScope.launch {
    val response = useCases.getInvoiceWithCustomer()
    if (response is Response.Success) {
        val sortedList = response.data.sortedByDescending { it.invoice.id }
        invoiceResponse = Response.Success(data = sortedList)
    } else {
        invoiceResponse = response
    }
}*/


    /* fun refreshData() {
         getInvoices()
     }*/
}
