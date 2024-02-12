package com.example.migestion.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.migestion.model.Product


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductDialog(
    product: Product,
    onAccept: (unitCost: String, quantity: Int, discount: Double, description: String) -> Unit,
    onDismissRequest: () -> Unit,
) {
        var unitCost by remember { mutableStateOf(product.price.toString()) }
        var quantity by remember { mutableStateOf("1") }
        var discount by remember { mutableStateOf("0") }
        var description by remember { mutableStateOf(product.description ?: "") }


        var selectType by remember { mutableStateOf(false) }
        var simbolType by remember { mutableStateOf(" %") }
        val discountTypes = listOf("Porcentaje", "Cantidad")


        Dialog(onDismissRequest = { onDismissRequest() }) {
            Card(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Text(
                        text = product.name,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                    Divider(modifier = Modifier.padding(8.dp))

                    TextField(
                        value = "$unitCost €",
                        onValueChange = {
                            val filteredText = it.filter { it.isDigit() || it == ',' }
                            unitCost = filteredText
                        },
                        placeholder = { Text(text = "Precio") },
                        singleLine = true,
                        label = { Text(text = "Coste unidad:") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    TextField(
                        value = quantity,
                        onValueChange = {
                            val filteredText = it.filter { it.isDigit() || it == ',' }
                            quantity = filteredText
                        },
                        placeholder = { Text(text = "1") },
                        singleLine = true,
                        label = { Text(text = "Cantidad:") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        TextField(
                            value = discount + simbolType,
                            onValueChange = {
                                val filteredText = it.filter { it.isDigit() || it == ',' }
                                discount = filteredText
                            },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            label = { Text(text = "Descuento %/€") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        IconButton(onClick = { selectType = true }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Desplegar menú")
                        }
                        DropdownMenu(
                            expanded = selectType,
                            onDismissRequest = { /* No hacemos nada al cerrar */ },
                            modifier = Modifier.wrapContentSize()
                        ) {
                            discountTypes.forEach { type ->
                                DropdownMenuItem(onClick = {
                                    if (type == "Porcentaje")
                                        simbolType = " %"
                                    else
                                        simbolType = " €"
                                    selectType = false
                                }, text = { Text(text = type) })
                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                    Row(
                        modifier = Modifier
                            .background(color = Color.Gray, shape = RoundedCornerShape(20.dp))
                            .padding(6.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Total:",
                            fontSize = 14.sp,
                            modifier = Modifier.weight(1f),
                            color = Color.White
                        )
                        var total = 0.0
                        val cleanedStr =
                            unitCost.replace(',', '.').filter { it.isDigit() || it == '.' }
                        val totalConverted = cleanedStr.toDoubleOrNull().toString()
                        if (totalConverted.isNotEmpty() && quantity.isNotEmpty())
                            total = totalConverted.toDouble() * quantity.toInt()
                        Text(
                            text = (total).toString() + " €",
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                    TextField(
                        value = description,
                        onValueChange = {
                            description = it
                        },
                        label = { Text(text = " Descripción:") },
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(20.dp)
                            ),
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = Color.Black,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ), shape = RoundedCornerShape(8.dp)
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Button(
                        onClick = {
                            onAccept(unitCost, quantity.toInt(), discount.toDouble(), description)
                            onDismissRequest()
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = "Añadir")
                    }

                }
            }
        }
}

@Preview(showBackground = true)
@Composable
fun MinimalDialog() {
    val product = Product(
        id = 1,
        name = "Producto de ejemplo",
        amount = 10,
        price = 29.99,
        category = "Electrónicos",
        createdAt = "2024-01-29",
        template = false,
        description = "Descripción del producto de ejemplo",
        invoice = null
    )
    //MinimalDialog2(product = product, true) {}
}