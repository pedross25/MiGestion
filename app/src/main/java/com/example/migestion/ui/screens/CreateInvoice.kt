package com.example.migestion.ui.screens

import android.widget.Space
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
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
import androidx.navigation.NavController
import com.example.migestion.ui.theme.BlueCobrado
import com.example.migestion.ui.theme.BlueInvoice
import com.example.migestion.ui.theme.TextGray

@Composable
fun CreateInvoiceScreen(navController: NavController) {

    Column {
        Header(navController)
        Surface(modifier = Modifier
            .fillMaxSize()
            .weight(0.7f),
            color = Color(0xFF45697B).copy(alpha = 0.15F)) {

            Column(modifier = Modifier.padding(16.dp)) {
                CustomerInvoice()
                Spacer(modifier = Modifier.padding(10.dp))
                AlbaranInvoice()

            }
        }
        Bottom()
    }

}

@Composable
fun Header(navController: NavController) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
        .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Default.Clear,
            contentDescription = null, // ContentDescription puede ser nulo si el icono es puramente decorativo
            tint = Color.Black, // Puedes ajustar el color de la cruz según tus necesidades
            modifier = Modifier
                .padding(4.dp) // Puedes ajustar el espacio alrededor del icono
                //.fillMaxSize() // Ocupará el tamaño máximo disponible
                .clickable {
                    navController.popBackStack()
                    // Acción al hacer clic en el icono (en este caso, volver atrás)
                    //onBack.invoke()
                }
        )
        Spacer(modifier = Modifier.padding(8.dp))

        Column {

            Text(text = "Crear factura", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Text(text = "30 Nov 2023", fontSize = 14.sp, fontWeight = FontWeight.Light, color = TextGray)
        }

        Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Bottom, modifier = Modifier.fillMaxSize()) {
            Text(text = "Invoice#135", fontSize = 12.sp, fontWeight = FontWeight.Normal)
        }
    }
}

/*@Composable
fun CustomerInvoice() {
    var isDialogVisible by remember { mutableStateOf(false) }
    var selectedClient by remember { mutableStateOf<String?>(null) }
    Row (modifier = Modifier
        .background(color = Color.White, shape = RoundedCornerShape(20.dp))
        .fillMaxWidth()
        .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
       ) {
        Text(text = "CLIENTE", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = BlueInvoice)

        Text(text = "Antonio Sánchez García@2711", fontSize = 14.sp, fontWeight = FontWeight.Normal)

        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Editar",
            tint = Color.Gray,
            modifier = Modifier
                //.padding(8.dp)
                .clickable {
                    // Lógica de manejo de clic para la edición
                    isDialogVisible = true
                }
        )
    }

    if (isDialogVisible) {
        ClientSelectionDialog { selectedClientName ->
            // Manejar la selección del cliente aquí
            selectedClient = selectedClientName
            isDialogVisible = false
        }
    }
}*/

@Composable
fun CustomerInvoice() {
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var selectedClient by remember { mutableStateOf<String?>(null) }

    val clientNames = listOf("Cliente 1", "Cliente 2", "Cliente 3", "Cliente 4") // Lista de nombres de clientes

    Row(
        modifier = Modifier
            .background(color = Color.White, shape = RoundedCornerShape(20.dp))
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
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
                //.weight(1f)
                .clickable { isDropdownExpanded = !isDropdownExpanded }
        ) {
            Text(
                text = selectedClient ?: "Seleccionar cliente",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray
            )

            if (isDropdownExpanded) {
                // Mostrar la lista desplegable
                DropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false },
                    modifier = Modifier.background(color = Color.White)
                ) {
                    clientNames.forEach { clientName ->
                        DropdownMenuItem(onClick = {
                            selectedClient = clientName
                            isDropdownExpanded = false
                        }, text = {Text(text = clientName)})
                            //Text(text = clientName)

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
                    // Lógica de manejo de clic para la edición
                    isDropdownExpanded = true
                }
        )
    }
}

@Composable
fun AlbaranInvoice() {
    Column(modifier = Modifier
        .background(color = Color.White, shape = RoundedCornerShape(20.dp))) {
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "ALBARAN", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = BlueInvoice)

            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Añadir albaran",
                tint = Color.Gray,
                modifier = Modifier
                    //.padding(8.dp)
                    .clickable {
                        // Lógica de manejo de clic para la edición
                    }
            )
        }
        Divider(
            color = Color.Gray
        )
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Text(text = "N127", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Mano de obra", fontSize = 14.sp, fontWeight = FontWeight.Normal)
                Text(text = "25 €", fontSize = 14.sp, fontWeight = FontWeight.Normal)
                Text(text = "2,5", fontSize = 14.sp, fontWeight = FontWeight.Normal)
                Text(text = "62,5 €", fontSize = 14.sp, fontWeight = FontWeight.Normal)
            }
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "ServerCost", fontSize = 14.sp, fontWeight = FontWeight.Normal)
                Text(text = "25 €", fontSize = 14.sp, fontWeight = FontWeight.Normal)
                Text(text = "2,5", fontSize = 14.sp, fontWeight = FontWeight.Normal)
                Text(text = "62,5 €", fontSize = 14.sp, fontWeight = FontWeight.Normal)
            }

        }
        Divider(
            color = Color.Gray
        )
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Subtotal", fontSize = 14.sp, fontWeight = FontWeight.Normal, color = TextGray)
                Text(text = "125 €", fontSize = 14.sp, fontWeight = FontWeight.Normal)
            }
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Tax", fontSize = 14.sp, fontWeight = FontWeight.Normal, color = TextGray)
                Text(text = "26,25 €", fontSize = 14.sp, fontWeight = FontWeight.Normal)
            }
        }
        Divider(
            color = Color.Gray
        )
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "TOTAL", fontSize = 14.sp, fontWeight = FontWeight.Normal, color = BlueInvoice)
                Text(text = "151,25 €", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
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
    val clientNames = listOf("Cliente 1", "Cliente 2", "Cliente 3", "Cliente 4") // Lista de nombres de clientes
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