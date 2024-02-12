package com.example.migestion.ui.screens.selectproductsscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.migestion.model.Product
import com.example.migestion.ui.components.AddProductDialog
import com.example.migestion.ui.components.BottomBarButton
import com.example.migestion.ui.components.NewSearchBar
import com.example.migestion.ui.components.ProductCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectProduct(
    onBack: () -> Unit, viewModel: SelectProductViewModel = hiltViewModel()
) {

    val state = viewModel.state

    // Muestra el dialogo para añadir productos
    state.selectedProduct?.let {
        AddProductDialog(product = it, onAccept = { unitCost, quantity, discount, description ->
            viewModel.onEvent(
                SelectProductEvent.AddProduct(
                    unitCost,
                    quantity,
                    discount,
                    description
                )
            )
            viewModel.onEvent(SelectProductEvent.CloseDialog)
        }, onDismissRequest = { viewModel.onEvent(SelectProductEvent.CloseDialog) })
    }

    // Vuelve atras cuando se hayan guardado los cambios
    LaunchedEffect(state.isSaved) {
        if (state.isSaved)
            onBack()
    }

    var isSearchActive by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (isSearchActive) {
                NewSearchBar(
                    onArrowIconClick = {
                        viewModel.onEvent(SelectProductEvent.CloseSearch)
                        isSearchActive = false
                    },
                    onClearIconClick = { viewModel.onEvent(SelectProductEvent.Search("")) },
                    onSearch = { text ->
                        viewModel.onEvent(SelectProductEvent.Search(text))
                    }
                )
            } else {
                CenterAlignedTopAppBar(title = {
                    Text(text = "Seleccionar productos")
                }, navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
                    }
                },
                    actions = {
                        IconButton(onClick = { isSearchActive = true }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Buscar"
                            )
                        }
                    })
            }
        },
        bottomBar = {
            BottomBarButton(
                "Continuar",
                onClick = { viewModel.onEvent(SelectProductEvent.FinishSelection) })
        }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFF45697B).copy(alpha = 0.15F))
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(8.dp)
            ) {
                val productList: List<Product> = if (state.isSearchActive) {
                    state.filteredProducts
                } else {
                    state.templateProducts
                }

                items(productList) { product ->
                    ProductCard(
                        product = product,
                        onClick = { viewModel.onEvent(SelectProductEvent.OpenDialog(product = product)) })
                }
            }
        }
    }
}