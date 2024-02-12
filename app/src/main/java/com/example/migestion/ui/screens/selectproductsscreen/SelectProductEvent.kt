package com.example.migestion.ui.screens.selectproductsscreen

import com.example.migestion.model.Product

sealed interface SelectProductEvent {
    data class AddProduct(
        val unitCost: String,
        val quantity: Int,
        val discount: Double,
        val description: String
    ) : SelectProductEvent

    data class OpenDialog(
        val product: Product
    ) : SelectProductEvent

    data class Search(val text: String) : SelectProductEvent

    data object CloseSearch : SelectProductEvent

    data object CloseDialog : SelectProductEvent
    data object FinishSelection : SelectProductEvent
}