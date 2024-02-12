package com.example.migestion.ui.screens.selectproductsscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.migestion.data.repositories.productrepository.ProductRepository
import com.example.migestion.model.Product
import com.example.migestion.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectProductViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val productRepository: ProductRepository
) : ViewModel() {

    var state by mutableStateOf(SelectProductState())
        private set

    init {
        val id = savedStateHandle.get<String?>("invoiceId")
        if (id != null) {
            viewModelScope.launch {
                state = state.copy(
                    templateProducts = productRepository.getTemplateProducts(),
                    id = id.toInt()
                )
                // TODO Que hacer con flow??
                /*productRepository.getProductsFromInvoice(id.toInt()).collectLatest {
                    when (it) {
                        is Response.Failure -> {}
                        Response.Loading -> {}
                        is Response.Success -> {
                            state = state.copy(addedProducts = it.data)
                        }
                    }
                }*/
            }
        }
    }

    fun onEvent(event: SelectProductEvent) {
        when (event) {
            is SelectProductEvent.AddProduct -> {
                val productModify = state.selectedProduct?.copy(
                    invoice = state.id,
                    price = event.unitCost.toDouble(),
                    amount = event.quantity,
                    description = event.description,
                    template = false,
                    parentId = state.selectedProduct!!.id
                )
                productModify?.let {
                    val newList = state.addedProducts.toMutableList()
                    newList.add(productModify)
                    state = state.copy(addedProducts = newList)
                }

            }

            SelectProductEvent.FinishSelection -> {
                viewModelScope.launch {
                    state.addedProducts.forEach { product ->
                        productRepository.addProduct(product = product)
                    }
                    state = state.copy(isSaved = true)
                }
            }

            SelectProductEvent.CloseDialog -> {
                state = state.copy(selectedProduct = null)
            }

            is SelectProductEvent.OpenDialog -> {
                state = state.copy(selectedProduct = event.product)
            }

            is SelectProductEvent.Search -> {
                viewModelScope.launch {
                    state = if (event.text.isEmpty()) {
                        state.copy(filteredProducts = state.templateProducts, isSearchActive = true)
                    } else {
                        val filteredList = state.templateProducts.filter { product ->
                            product.name.contains(event.text, ignoreCase = true)
                        }
                        state.copy(filteredProducts = filteredList, isSearchActive = true)
                    }
                }
            }

            SelectProductEvent.CloseSearch -> {
                state = state.copy(filteredProducts = emptyList(), isSearchActive = false)
            }
        }
    }
}