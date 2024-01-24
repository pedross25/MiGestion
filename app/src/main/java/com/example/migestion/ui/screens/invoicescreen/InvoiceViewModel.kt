package com.example.migestion.ui.screens.invoicescreen

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.migestion.data.repositories.customerrepository.CustomerRepository
import com.example.migestion.data.repositories.invoicerepository.InvoiceRepository
import com.example.migestion.data.repositories.invoicerepository.InvoiceResponse
import com.example.migestion.model.Customer
import com.example.migestion.model.Invoice
import com.example.migestion.model.Response
import com.example.migestion.usecases.InvoiceWithCustomer
import com.example.migestion.usecases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class InvoiceViewModel @Inject constructor(
    private val useCases: UseCases,
) : ViewModel() {

    var invoiceResponse by mutableStateOf<Response<List<InvoiceWithCustomer>>>(Response.Loading)
        private set

    init {
        getInvoices()
    }

    private fun getInvoices() = viewModelScope.launch {
        invoiceResponse = useCases.getInvoiceWithCustomer()
        Log.i("PRUEBA", invoiceResponse.toString())
    }




}
