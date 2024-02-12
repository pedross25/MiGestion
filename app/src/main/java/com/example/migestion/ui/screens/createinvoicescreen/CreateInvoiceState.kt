package com.example.migestion.ui.screens.createinvoicescreen

import com.example.migestion.model.Customer
import com.example.migestion.model.Product

data class CreateInvoiceState (
    val customerList: List<Customer> = emptyList(),
    val filteredList: List<Customer> = emptyList(),
    val selectCustomer: Customer? = null,
    val isSearchingCustomer: Boolean = false,
    val currentDate: String = "",
    val idInvoice: Int? = null,
    val productList: List<Product> = emptyList()
)