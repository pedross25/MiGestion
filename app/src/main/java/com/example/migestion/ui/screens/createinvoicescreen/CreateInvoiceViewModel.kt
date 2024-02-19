package com.example.migestion.ui.screens.createinvoicescreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.migestion.core.getFormattedCurrentDate
import com.example.migestion.data.repositories.customerrepository.CustomerRepository
import com.example.migestion.data.repositories.invoicerepository.InvoiceRepository
import com.example.migestion.data.repositories.productrepository.ProductRepository
import com.example.migestion.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.toImmutableList
import javax.inject.Inject

@HiltViewModel
class CreateInvoiceViewModel @Inject constructor(
    private val invoiceRepository: InvoiceRepository,
    private val customerRepository: CustomerRepository,
    private val productRepository: ProductRepository,
) : ViewModel() {

    private var _uiState = MutableStateFlow(CreateInvoiceState())
    val uiState = _uiState.asStateFlow()

    var searchQuery by mutableStateOf("")
        private set

    init {
        initializeUiState()
    }

    private fun initializeUiState() {
        viewModelScope.launch {
            _uiState.update { it.copy(currentDate = getFormattedCurrentDate()) }
        }
        getNumInvoice()
        getCustomers()
        getProducts()

        snapshotFlow { searchQuery }
            .debounce(350L)
            .onEach { query ->
                if (query.isNotBlank()) {
                    _uiState.update {
                        it.copy(isSearchingCustomer = true)
                    }

                    // TODO Sacar a caso de uso
                    val filteredList = _uiState.value.customerList.filter { customer ->
                        customer.businessName.contains(query, ignoreCase = true)
                    }

                    _uiState.update {
                        it.copy(filteredList = filteredList, isSearchingCustomer = false)
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            filteredList = _uiState.value.customerList,
                            isSearchingCustomer = false
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun onEvent(event: CreateInvoiceEvent) {
        when (event) {
            is CreateInvoiceEvent.SearchCustomer -> {
                searchQuery = event.text
                /*_uiState.update {
                    if (event.text.isEmpty()) {
                        it.copy(
                            filteredList = uiState.value.customerList,
                            isSearchingCustomer = true
                        )
                    } else {
                        val filteredList = uiState.value.customerList.filter { customer ->
                            customer.businessName.contains(event.text, ignoreCase = true)
                        }
                        it.copy(filteredList = filteredList, isSearchingCustomer = true)
                    }
                }*/
            }

            CreateInvoiceEvent.CloseSearch -> _uiState.update {
                it.copy(filteredList = uiState.value.customerList, isSearchingCustomer = false)
            }

            is CreateInvoiceEvent.SelectCustomer -> _uiState.update {
                it.copy(selectCustomer = event.customer)
            }

            //TODO HACER QUE ESPERE A QUE TERMINE EL PROCESO PARA VOLVER ATRAS?
            CreateInvoiceEvent.GenerateInvoice -> {
                viewModelScope.launch {
                    var totalPrice = 0.0
                    uiState.value.productList.forEach { product ->
                        totalPrice += (product.amount * product.price)
                    }
                    if (uiState.value.idInvoice != null && uiState.value.selectCustomer != null) {
                        invoiceRepository.createInvoice(
                            uiState.value.idInvoice!!,
                            listOf(),
                            uiState.value.selectCustomer!!.id,
                            1,
                            getFormattedCurrentDate(),
                            finished = true,
                            paid = false,
                            totalPrice = totalPrice
                        )
                        productRepository.persistProductsFromInvoice(uiState.value.idInvoice!!)
                    }
                }
            }
        }
    }

    private fun getCustomers() = viewModelScope.launch {
        when (val customerList = customerRepository.getAll()) {
            is Response.Failure -> TODO()
            Response.Loading -> TODO()
            is Response.Success -> {
                _uiState.update {
                    it.copy(
                        customerList = customerList.data,
                        filteredList = customerList.data
                    )
                }
            }
        }
    }

    private fun getNumInvoice() = viewModelScope.launch {
        _uiState.update { it.copy(idInvoice = invoiceRepository.getNextId()) }
    }

    /*private fun getProducts() = viewModelScope.launch {
        _uiState.value.idInvoice?.let {
            productRepository.getProductsFromInvoice(it).onEach { productsResult ->
                when (productsResult) {
                    is Response.Success -> {
                        val products = productsResult.data.toImmutableList()
                        _uiState.update {
                            it.copy(productList = products)
                        }
                    }

                    is Response.Failure -> {}
                    Response.Loading -> {}
                }
            }
        }
    }*/

    private fun getProducts() = viewModelScope.launch {
        _uiState.value.idInvoice?.let {
            productRepository.getProductsFromInvoice(it).collectLatest { productsResult ->
                when(productsResult) {
                    is Response.Failure -> {}
                    Response.Loading -> {}
                    is Response.Success -> {
                        _uiState.update {
                            it.copy(productList = productsResult.data)
                        }
                    }
                }
            }
        }
    }

}