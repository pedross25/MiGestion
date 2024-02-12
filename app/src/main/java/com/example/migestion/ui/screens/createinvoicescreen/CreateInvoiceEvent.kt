package com.example.migestion.ui.screens.createinvoicescreen

import com.example.migestion.model.Customer

sealed interface CreateInvoiceEvent {

    data class SearchCustomer(val text: String): CreateInvoiceEvent
    data object CloseSearch: CreateInvoiceEvent

    data class SelectCustomer(val customer: Customer): CreateInvoiceEvent

    data object GenerateInvoice: CreateInvoiceEvent
}