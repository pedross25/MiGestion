package com.example.migestion.ui.screens.selectproductsscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.migestion.data.repositories.productrepository.ProductRepository
import com.example.migestion.model.Product
import com.example.migestion.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectProductViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val productRepository: ProductRepository
) : ViewModel() {

    private var _uiState = MutableStateFlow(SelectProductState())
    val uiState = _uiState.asStateFlow()

    var searchQuery by mutableStateOf("")
        private set

    init {
        val id = savedStateHandle.get<String?>("invoiceId")
        initialiseUiState(id)
    }

    @OptIn(FlowPreview::class)
    private fun initialiseUiState(id: String?) {
        viewModelScope.launch {
            if (id != null) {
                _uiState.update { it.copy(isSearchActive = true, id = id.toInt()) }
                val products = productRepository.getTemplateProducts()
                _uiState.update {
                    it.copy(
                        templateProducts = products,
                        filteredProducts = products,
                        isSearchActive = false
                    )
                }
            }
        }

        snapshotFlow { searchQuery }
            .debounce(350L)
            .onEach { query ->
                if (query.isNotBlank()) {
                    _uiState.update {
                        it.copy(isSearchActive = true)
                    }

                    // TODO Sacar a caso de uso??
                    val filteredList = _uiState.value.templateProducts.filter { product ->
                        product.name.contains(query, ignoreCase = true)
                    }

                    _uiState.update {
                        it.copy(filteredProducts = filteredList, isSearchActive = false)
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            filteredProducts = _uiState.value.templateProducts,
                            isSearchActive = false
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun onEvent(event: SelectProductEvent) {
        when (event) {
            is SelectProductEvent.AddProduct -> {
                val productModify = uiState.value.selectedProduct?.copy(
                    invoice = uiState.value.id,
                    price = event.unitCost.toDouble(),
                    amount = event.quantity,
                    description = event.description,
                    template = false,
                    parentId = uiState.value.selectedProduct!!.id
                )
                productModify?.let {
                    val newList = uiState.value.addedProducts.toMutableList()
                    newList.add(productModify)
                    _uiState.update {
                        it.copy(addedProducts = newList)
                    }
                }

            }

            SelectProductEvent.FinishSelection -> {
                viewModelScope.launch {
                    _uiState.value.addedProducts.forEach { product ->
                        productRepository.addProduct(product = product)
                    }
                    _uiState.update {
                        it.copy(isSaved = true)
                    }
                }
            }

            SelectProductEvent.CloseDialog -> {
                _uiState.update {
                    it.copy(selectedProduct = null)
                }
            }

            is SelectProductEvent.OpenDialog -> {
                _uiState.update {
                    it.copy(selectedProduct = event.product)
                }
            }

            SelectProductEvent.CloseSearch -> {
                _uiState.update {
                    it.copy(
                        filteredProducts = _uiState.value.templateProducts,
                        isSearchActive = false
                    )
                }
            }

            is SelectProductEvent.Search -> {
                searchQuery = event.text
            }
        }
    }
}