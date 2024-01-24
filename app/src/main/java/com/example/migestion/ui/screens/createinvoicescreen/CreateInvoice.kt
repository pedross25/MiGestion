package com.example.migestion.ui.screens.createinvoicescreen

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.migestion.model.Customer
import com.example.migestion.ui.navigation.graphs.DetailsScreen
import com.example.migestion.ui.theme.BlueInvoice
import com.example.migestion.ui.theme.TextGray

@Composable
fun CreateInvoiceScreen(
    navController: NavController,
    viewModel: CreateInvoiceViewModel = hiltViewModel()
) {

    Column {
        Header(navController)
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.7f),
            color = Color(0xFF45697B).copy(alpha = 0.15F)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                CustomerInvoice(viewModel.customers)
                Spacer(modifier = Modifier.padding(10.dp))
                AlbaranInvoice(navController)
            }
        }
        Bottom()
    }

}

@Composable
fun Header(navController: NavController, viewModel: CreateInvoiceViewModel = hiltViewModel()) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Clear,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier
                .padding(4.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
        Spacer(modifier = Modifier.padding(8.dp))

        Column {

            Text(text = "Crear factura", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Text(
                text = viewModel.currentDate,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                color = TextGray
            )
        }

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Invoice#" + viewModel.idInvoice,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Composable
fun CustomerInvoice(
    customers: List<Customer>,
    viewModel: CreateInvoiceViewModel = hiltViewModel()
) {
    var isDropdownExpanded by remember { mutableStateOf(false) }
    val selectedCustomer = viewModel.selectedCustomer

    Row(
        modifier = Modifier
            .background(color = Color.White, shape = RoundedCornerShape(20.dp))
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "CLIENTE",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = BlueInvoice
        )

        Box(
            modifier = Modifier
                .padding(start = 16.dp)
        ) {
            Text(
                text = selectedCustomer?.businessName ?: "Seleccionar cliente",
                fontSize = 16.sp,
                fontWeight = if (selectedCustomer == null) {
                    FontWeight.Normal
                } else {
                    FontWeight.SemiBold
                },
                color = if (selectedCustomer == null) {
                    Color.Gray
                } else {
                    Color.Black
                },
                modifier = Modifier.clickable {
                    isDropdownExpanded = !isDropdownExpanded
                }
            )

            if (isDropdownExpanded) {
                DropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false },
                    modifier = Modifier.background(color = Color.White)
                ) {
                    customers.forEach { customer ->
                        DropdownMenuItem(onClick = {
                            viewModel.selectCustomer(customer)
                            isDropdownExpanded = false
                        }, text = { Text(text = customer.businessName) })
                    }
                }
            }
        }

        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Editar",
            tint = Color.Gray,
            modifier = Modifier
                .clickable {
                    isDropdownExpanded = true
                }
                .wrapContentWidth(Alignment.End)
                .weight(1f)
        )
    }
}

@Composable
fun AlbaranInvoice(navController: NavController) {
    Column(
        modifier = Modifier
            .background(color = Color.White, shape = RoundedCornerShape(20.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "PRODUCTOS",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = BlueInvoice
            )

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Añadir albaran",
                tint = Color.Gray,
                modifier = Modifier
                    .clickable {
                        navController.navigate(DetailsScreen.SelectProducts.route)
                    }
            )
        }
    }
}

@Composable
fun Bottom() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Button(/*colors = , */onClick = { /*TODO*/ }) {
            Text(text = "Crear", color = Color.White)
        }
    }
}

@Composable
fun ClientSelectionDialog(onClientSelected: (String) -> Unit) {
    val clientNames =
        listOf("Cliente 1", "Cliente 2", "Cliente 3", "Cliente 4") // Lista de nombres de clientes
    var selectedClient by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = { /* No hacer nada al cerrar */ },
        title = { Text("Seleccionar Cliente") },
        text = {
            Column {
                clientNames.forEach { clientName ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedClient = clientName
                            }
                            .padding(8.dp)
                            .background(if (clientName == selectedClient) Color.Gray.copy(alpha = 0.3f) else Color.Transparent)
                    ) {
                        Text(text = clientName)
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    selectedClient?.let { onClientSelected(it) }
                }
            ) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    // Cierra el diálogo sin seleccionar ningún cliente
                }
            ) {
                Text("Cancelar")
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun CreateInvoiceScreenPreview() {
    //CreateInvoiceScreen()
}