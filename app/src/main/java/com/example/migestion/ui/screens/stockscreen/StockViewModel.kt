package com.example.migestion.ui.screens.stockscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.migestion.data.repositories.productrepository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class StockViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {


    init {
        initialiseUI()
    }

    fun initialiseUI() = viewModelScope.launch {

        productRepository.getTemplateProducts()
    }




}