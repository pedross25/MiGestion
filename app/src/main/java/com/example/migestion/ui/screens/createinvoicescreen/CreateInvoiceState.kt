package com.example.migestion.ui.screens.createinvoicescreen

import com.example.migestion.model.Customer
import com.example.migestion.model.Product
import kotlinx.collections.immutable.persistentListOf

data class CreateInvoiceState(
    val customerList: List<Customer> = emptyList(),
    val filteredList: List<Customer> = emptyList(),
    val selectCustomer: Customer? = null,
    val isSearchingCustomer: Boolean = false,
    val currentDate: String = "",
    val idInvoice: Int? = null,
    val productList: List<Product> = persistentListOf(),
    val errorMessageIds: List<Int> = persistentListOf()
)