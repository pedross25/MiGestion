package com.example.migestion.ui.screens.createinvoicescreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.migestion.ui.components.BottomBarButton
import com.example.migestion.ui.components.NewSearchBar
import com.example.migestion.ui.components.ProductListView
import com.example.migestion.ui.theme.BlueInvoice
import com.example.migestion.ui.theme.TextGray

@Composable
fun CreateInvoiceScreen(
    onSelectProduct: (String) -> Unit,
    onBack: () -> Unit,
    viewModel: CreateInvoiceViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { Header(onBack, uiState) },
        bottomBar = {
            BottomBarButton("Crear") {
                viewModel.onEvent(CreateInvoiceEvent.GenerateInvoice)
                onBack()
            }
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFF45697B).copy(alpha = 0.15F))
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .padding(it)

            ) {
                SelectCustomerToInvoice(viewModel, uiState)
                Spacer(modifier = Modifier.padding(10.dp))
                AlbaranInvoice(onSelectProduct = onSelectProduct, uiState)
            }
        }
    }
}

@Composable
fun Header(onBack: () -> Unit, uiState: CreateInvoiceState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .height(60.dp)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(imageVector = Icons.Default.Clear,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier
                .padding(4.dp)
                .clickable {
                    onBack()
                })
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "Crear factura", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Text(
                text = uiState.currentDate,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                color = TextGray
            )
        }
        Text(
            text = "Invoice#" + uiState.idInvoice,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.align(Alignment.Bottom)
        )
    }
}

@Composable
fun SelectCustomerToInvoice(
    viewModel: CreateInvoiceViewModel,
    uiState: CreateInvoiceState
) {
    var expanded by remember { mutableStateOf(false) }
    var search by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(color = Color.White, shape = RoundedCornerShape(20.dp))
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (search) {
                NewSearchBar(onArrowIconClick = {
                    search = false
                    viewModel.onEvent(CreateInvoiceEvent.CloseSearch)
                },
                    onSearch = { text -> viewModel.onEvent(CreateInvoiceEvent.SearchCustomer(text)) },
                    onClearIconClick = { viewModel.onEvent(CreateInvoiceEvent.SearchCustomer("")) },
                    onQueryChange = { text -> viewModel.onEvent(CreateInvoiceEvent.SearchCustomer(text)) })
            } else {
                Text(
                    text = "CLIENTE",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = BlueInvoice
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(text = uiState.selectCustomer?.businessName ?: "Seleccionar cliente",
                    fontSize = 16.sp,
                    fontWeight = if (uiState.selectCustomer?.businessName == null) {
                        FontWeight.Normal
                    } else {
                        FontWeight.SemiBold
                    },
                    color = if (uiState.selectCustomer?.businessName == null) {
                        Color.Gray
                    } else {
                        Color.Black
                    },
                    modifier = Modifier.clickable {
                        expanded = !expanded
                    })
            }
            Icon(imageVector = if (expanded) {
                Icons.Default.Search
            } else {
                Icons.Default.Edit
            }, contentDescription = "Editar", tint = Color.Gray, modifier = Modifier
                .clickable {
                    if (expanded) {
                        search = true
                    }
                    expanded = true
                }
                .wrapContentWidth(Alignment.End)
                .weight(1f))
        }
        AnimatedVisibility(visible = expanded) {
            LazyColumn {
                items(uiState.filteredList) { customer ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable(onClick = {
                                viewModel.onEvent(
                                    CreateInvoiceEvent.SelectCustomer(
                                        customer
                                    )
                                )
                                expanded = false
                                viewModel.onEvent(CreateInvoiceEvent.CloseSearch)
                                search = false
                            })
                    ) {
                        Text(
                            text = customer.businessName, modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                }
                /*if (viewModel.uiState..isNotEmpty()) {
                    val errorMessage = stringResource(uiState.errorMessageIds.first())

                    LaunchedEffect(errorMessage, snackbarHostState) {
                        snackbarHostState.showSnackbar(message = errorMessage)
                        onDismissError(uiState.errorMessageIds.first())
                    }
                }*/
            }
        }
    }
}

@Composable
fun AlbaranInvoice(
    onSelectProduct: (String) -> Unit, uiState: CreateInvoiceState
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.background(color = Color.White, shape = RoundedCornerShape(20.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "PRODUCTOS",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = BlueInvoice,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = "Añadir albaran",
                tint = Color.Gray,
                modifier = Modifier.clickable { expanded = !expanded })
            Spacer(modifier = Modifier.padding(8.dp))
            Icon(imageVector = Icons.Default.Add,
                contentDescription = "Añadir albaran",
                tint = Color.Gray,
                modifier = Modifier.clickable {
                    onSelectProduct(uiState.idInvoice.toString())
                })
        }

        AnimatedVisibility(visible = expanded) {
            LazyColumn {
                items(uiState.productList) { product ->
                    ProductListView(product)
                }
            }
        }
    }
}