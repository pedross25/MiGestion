package com.example.migestion.ui.screens.customerscreen

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.migestion.data.db.CustomerEntity
import com.example.migestion.data.repositories.customerrepository.CustomerRepository
import com.example.migestion.model.Customer
import com.example.migestion.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val repository: CustomerRepository
) : ViewModel() {

    var customerResponse by mutableStateOf<Response<List<Customer>>>(Response.Loading)
        private set

    private val allCustomers = mutableListOf<Customer>()

    private val _filteredCustomers = mutableStateOf(allCustomers)
    val filteredCustomers get() = _filteredCustomers.value
    val context = context

    init {
        getCustomers()
    }


    private fun getCustomers() = viewModelScope.launch {
        val customersResponse = repository.getAll()
        when(customersResponse) {
            is Response.Success -> {
                val customers = customersResponse.data
                customerResponse = customersResponse
                val orderedList = customers.sortedBy { it.businessName }
                allCustomers.addAll(orderedList)
                _filteredCustomers.value = orderedList.toMutableList()
            }

            is Response.Failure -> TODO()
            Response.Loading -> TODO()
        }
    }

    private fun insertAndSortNewCustomer(customer: Customer) {
        allCustomers.add(customer)
        val orderedList = allCustomers.sortedBy { it.businessName }
        _filteredCustomers.value = orderedList.toMutableList()

    }

    fun createCustomer(
        name: String,
        streetAddress: String,
        city: String,
        state: String,
        postalCode: String,
        email: String,
        phoneNumber: String,
        cif: String
    ) = viewModelScope.launch {
        repository.createCustomer(
            name,
            streetAddress,
            city,
            state,
            postalCode,
            email,
            phoneNumber,
            cif
        ).collect { response ->
            when (response) {
                is Response.Success -> {
                    insertAndSortNewCustomer(response.data)
                }
                is Response.Failure -> {
                    // Manejar el caso de error, por ejemplo, mostrar un mensaje de error
                    //val errorMessage = response.exception.message
                    // Haz algo con el mensaje de error si es necesario
                }

                is Response.Loading -> {
                    // Puedes manejar el estado de carga si es necesario
                }
            }
        }
    }

    fun searchCustomers(query: String) {
        viewModelScope.launch {
            _filteredCustomers.value = filterCustomers(query).toMutableList()
        }
    }


    private fun filterCustomers(searchText: String): List<Customer> {
        return if (searchText.isEmpty()) {
            allCustomers
        } else {
            allCustomers.filter { customer ->
                customer.businessName.contains(searchText, ignoreCase = true)
            }
        }
    }

}