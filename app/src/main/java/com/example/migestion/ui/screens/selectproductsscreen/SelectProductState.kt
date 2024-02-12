package com.example.migestion.ui.screens.selectproductsscreen

import com.example.migestion.model.Product

data class SelectProductState(
    val id: Int? = null,
    val isSaved: Boolean = false,
    val isSearchActive: Boolean = false,
    val templateProducts: List<Product> = emptyList(),
    val addedProducts: List<Product> = emptyList(),
    val filteredProducts: List<Product> = emptyList(),
    val selectedProduct: Product? = null
)