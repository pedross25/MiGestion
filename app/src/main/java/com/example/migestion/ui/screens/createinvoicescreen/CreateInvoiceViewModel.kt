package com.example.migestion.ui.screens.createinvoicescreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.migestion.data.repositories.customerrepository.CustomerRepository
import com.example.migestion.data.repositories.invoicerepository.InvoiceRepository
import com.example.migestion.model.Customer
import com.example.migestion.model.Response
import com.example.migestion.usecases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CreateInvoiceViewModel @Inject constructor(
    private val useCases: UseCases,
    private val invoiceRepository: InvoiceRepository,
    private val customerRepository: CustomerRepository
) : ViewModel() {

    var idInvoice by mutableIntStateOf(0)
        private set

    var customers by mutableStateOf<List<Customer>>(listOf())
        private set

    var currentDate by mutableStateOf("")
        private set

    var selectedCustomer by mutableStateOf<Customer?>(null)
        private set


    init {
        viewModelScope.launch {
            currentDate = getFormattedCurrentDate()
        }
        getNumInvoice()
        getCustomers()

    }

    private fun getNumInvoice() = viewModelScope.launch {
        idInvoice = invoiceRepository.getNextId()
    }

    private fun getCustomers() = viewModelScope.launch {
        val cust = customerRepository.getAll()
        if (cust is Response.Success) {
            customers = cust.data
        }
    }

    // Sacar del viewModel?

    private fun getFormattedCurrentDate(): String {
        val currentDateTime = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale("es"))
        return currentDateTime.format(formatter)
    }

    /**
     * Evento de seleccionar un cliente
     */
    fun selectCustomer(customer: Customer) = viewModelScope.launch {
        selectedCustomer = customer
    }




}