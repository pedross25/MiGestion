package com.example.migestion.ui.screens.selectproductsscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.migestion.data.repositories.productrepository.ProductRepository
import com.example.migestion.model.Product
import com.example.migestion.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    var productsResponse by mutableStateOf<Response<List<Product>>>(Response.Loading)
        private set

    var listProduct by mutableStateOf<List<Product>>(mutableListOf())
        private set

    var openDialog by mutableStateOf(false)
        private set

    var selectProduct by mutableStateOf<Product?>(null)
        private set

    var numInvoice by mutableStateOf(0)
        private set


    init {
        getProducts()
    }

    private fun getProducts() = viewModelScope.launch {
        productsResponse = productRepository.getTemplateProducts()
        productRepository.getProductsFromInvoice(numInvoice).collect {
            when (it) {
                is Response.Failure -> {}
                Response.Loading -> {
                }
                is Response.Success -> {
                    listProduct = it.data
                }
            }
        }
    }

    fun finishSelection() = viewModelScope.launch {
        listProduct.forEach {
            productRepository.addProduct(it)
        }
    }

    fun addProduct(unitCost: Double, quantity: Int, discount: Double, description: String) =
        viewModelScope.launch {
            selectProduct?.let {
                val productModify = it.copy(
                    invoice = numInvoice,
                    price = unitCost,
                    amount = quantity,
                    description = description,
                    template = false
                )
                val list = listProduct.toMutableList()
                list.add(productModify)
                listProduct = list
            }
        }

    fun openDialog(product: Product) {
        selectProduct = product
        openDialog = true
    }

    /**
     * Cierra el diálogo y pone a false el response de la puja
     */
    fun closeDialog() {
        openDialog = false
    }

    //TODO Revisar
    fun setInvoice(num: Int) {
        numInvoice = num
    }
}