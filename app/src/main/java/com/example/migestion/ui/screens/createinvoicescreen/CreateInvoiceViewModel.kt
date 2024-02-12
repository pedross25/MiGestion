package com.example.migestion.ui.screens.createinvoicescreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.migestion.core.getFormattedCurrentDate
import com.example.migestion.data.repositories.customerrepository.CustomerRepository
import com.example.migestion.data.repositories.invoicerepository.InvoiceRepository
import com.example.migestion.data.repositories.productrepository.ProductRepository
import com.example.migestion.model.Product
import com.example.migestion.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CreateInvoiceViewModel @Inject constructor(
    private val invoiceRepository: InvoiceRepository,
    private val customerRepository: CustomerRepository,
    private val productRepository: ProductRepository,
) : ViewModel() {

    var state by mutableStateOf(CreateInvoiceState())
        private set

    init {
        viewModelScope.launch {
            state = state.copy(currentDate = getFormattedCurrentDate())
        }
        getNumInvoice()
        getCustomers()
        getProducts()
    }

    fun onEvent(event: CreateInvoiceEvent) {
        when (event) {
            is CreateInvoiceEvent.SearchCustomer -> {
                state = if (event.text.isEmpty()) {
                    state.copy(filteredList = state.customerList, isSearchingCustomer = true)
                } else {
                    val filteredList = state.customerList.filter { customer ->
                        customer.businessName.contains(event.text, ignoreCase = true)
                    }
                    state.copy(filteredList = filteredList, isSearchingCustomer = true)
                }
            }

            CreateInvoiceEvent.CloseSearch -> state =
                state.copy(filteredList = emptyList(), isSearchingCustomer = false)

            is CreateInvoiceEvent.SelectCustomer -> state =
                state.copy(selectCustomer = event.customer)

            //TODO HACER QUE ESPERE A QUE TERMINE EL PROCESO PARA VOLVER ATRAS?
            CreateInvoiceEvent.GenerateInvoice -> {
                viewModelScope.launch {
                    var totalPrice = 0.0
                    state.productList.forEach { product ->
                        totalPrice += (product.amount * product.price)
                    }
                    if (state.idInvoice != null && state.selectCustomer != null) {
                        invoiceRepository.createInvoice(
                            state.idInvoice!!,
                            listOf(),
                            state.selectCustomer!!.id,
                            1,
                            getFormattedCurrentDate(),
                            finished = true,
                            paid = false,
                            totalPrice = totalPrice
                        )
                        productRepository.persistProductsFromInvoice(state.idInvoice!!)
                    }
                }
            }
        }
    }

    private fun getCustomers() = viewModelScope.launch {
        val customerList = customerRepository.getAll()
        if (customerList is Response.Success) {
            state = state.copy(customerList = customerList.data)
        }
    }

    private fun getNumInvoice() = viewModelScope.launch {
        state = state.copy(idInvoice = invoiceRepository.getNextId())
    }

    private fun getProducts() = viewModelScope.launch {
        state.idInvoice?.let {
            productRepository.getProductsFromInvoice(it).collectLatest { products ->
                if (products is Response.Success) {
                    state = state.copy(productList = products.data)
                }
            }
        }
    }
}