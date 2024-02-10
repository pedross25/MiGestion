package com.example.migestion.ui.screens.customerscreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.migestion.model.Customer
import com.example.migestion.model.Response
import com.example.migestion.ui.components.BarraBusqueda
import com.example.migestion.ui.components.ProgressBar

@Composable
fun CustomerScreen(viewModel: CustomerViewModel = hiltViewModel(), onItemClick: (String) -> Unit) {

    Customers(viewModel = viewModel) {
        var showClientForm by remember { mutableStateOf(false) }

        if (showClientForm) {
            ClientForm()
        }

        Column {
            BarraBusqueda()
            CustomerList(customers = it, viewModel = viewModel, onItemClick = {})
        }

    }
}

@Composable
fun Customers(
    viewModel: CustomerViewModel = hiltViewModel(),
    productsContent: @Composable (products: List<Customer>) -> Unit
) {
    when (val productsResponse = viewModel.customerResponse) {
        is Response.Loading -> ProgressBar()
        is Response.Success -> productsContent(viewModel.filteredCustomers)
        is Response.Failure -> print(productsResponse.e)
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomerList(
    customers: List<Customer>,
    onItemClick: (String) -> Unit,
    viewModel: CustomerViewModel
) {
    /*val items = listOf(
        MenuItem("Llamar", R.drawable.call),
        MenuItem("Editar", R.drawable.edit),
        MenuItem("Facturas", R.drawable.baseline_attach_money_24),
        MenuItem("Más", R.drawable.more)
    )
*/
    var selectedIndex by remember { mutableIntStateOf(-1) }

    // Estado para manejar el cliente seleccionado
    var selectedCustomer by remember { mutableStateOf<Customer?>(null) }

    // Estado para controlar la visibilidad del menú emergente
    var isMenuVisible by remember { mutableStateOf(false) }

    // Estado para manejar la posición del menú emergente
    var menuPosition by remember { mutableStateOf(IntOffset(0, 0)) }

    var isAddClientPopupVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 70.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            //val groupedClientes = customers.groupBy { it.businessName.first().uppercaseChar() }
            val groupedClientes: Map<String, List<Customer>> = customers.groupBy {
                (it.businessName.firstOrNull()?.uppercaseChar() ?: "Sin nombre").toString()
            }

            LazyColumn {
                groupedClientes.forEach { (letra, clientesConLetra) ->
                    stickyHeader {
                        Text(
                            text = letra,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.background)
                                .padding(8.dp),
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                    items(clientesConLetra) { customer ->
                        CustomerItem(
                            customer = customer
                        ) {
                            selectedCustomer = customer
                            isMenuVisible = true
                        }
                    }

                }
            }

            if (isMenuVisible) {
                val menuHeight =
                    with(LocalDensity.current) { (LocalDensity.current.density * 100).dp }
                val menuWidth =
                    with(LocalDensity.current) { (LocalDensity.current.density * 200).dp }

            }
            DropdownMenu(
                expanded = isAddClientPopupVisible,
                onDismissRequest = { isAddClientPopupVisible = false },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.primary,
                        shape = MaterialTheme.shapes.medium
                    )
            ) {
                //AddClientFields(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun CustomerItem(customer: Customer, onItemClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onItemClick)
    ) {
        Text(
            text = customer.businessName,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ClientForm() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var optionalFields by remember { mutableStateOf(listOf("Teléfono", "Dirección", "CP")) }
    var selectedFieldIndex by remember { mutableStateOf(-1) }
    var isAddingField by remember { mutableStateOf(false) }

    var isNameError by remember { mutableStateOf(false) }
    var isEmailError by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current


    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        // Name field
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                isNameError = it.isEmpty()
            },
            label = { Text("Name") },
            isError = isNameError,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Email field
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                isEmailError = it.isEmpty()
            },
            label = { Text("Email") },
            isError = isEmailError,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Optional fields
        optionalFields.forEachIndexed { index, field ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        isEmailError = it.isEmpty()
                    },
                    label = { Text(field) },
                    isError = isEmailError,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 8.dp)
                )

                IconButton(
                    onClick = {
                        // Eliminar campo opcional
                        optionalFields = optionalFields.toMutableList().apply {
                            removeAt(index)
                        }
                    }
                ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }
            }
        }

        // Botón para agregar campo opcional
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = {
                    // Mostrar diálogo para agregar nuevo campo opcional
                    isAddingField = true
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }

        // Mostrar diálogo para agregar nuevo campo opcional
        if (isAddingField) {
            AddOptionalFieldDialog(
                onDismiss = {
                    // Restablecer el índice seleccionado
                    selectedFieldIndex = -1
                    // Cerrar el teclado si estaba abierto
                    keyboardController?.hide()
                    // Restablecer el estado de agregar campo
                    isAddingField = false
                },
                onAddField = { newField ->
                    if (selectedFieldIndex != -1) {
                        // Editar el campo existente
                        optionalFields = optionalFields.toMutableList().apply {
                            set(selectedFieldIndex, newField)
                        }
                    } else {
                        // Agregar nuevo campo
                        optionalFields = optionalFields + newField
                    }
                },
                initialField = if (selectedFieldIndex != -1) optionalFields[selectedFieldIndex] else ""
            )
        }

        // Guardar button
        Button(
            onClick = {
                // Realizar acciones de guardado aquí
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Icon(imageVector = Icons.Default.Check, contentDescription = "Save")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Save")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOptionalFieldDialog(
    onDismiss: () -> Unit,
    onAddField: (String) -> Unit,
    initialField: String = ""
) {
    var newField by remember { mutableStateOf(initialField) }
    var selectedAttribute by remember { mutableStateOf<String?>(null) }

    // Lista de atributos predefinidos
    val predefinedAttributes = listOf("CIF", "Dirección", "Ciudad")

    AlertDialog(
        onDismissRequest = onDismiss,
        //modifier = Modifier.background(Color.Gray),
        content = {
            Column {
                // Mostrar la lista de atributos predefinidos
                if (selectedAttribute == null) {
                    for (attribute in predefinedAttributes) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    selectedAttribute = attribute
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(attribute)
                        }
                    }
                }

                // Mostrar el campo de texto si no se ha seleccionado un atributo
                if (selectedAttribute == null) {
                    OutlinedTextField(
                        value = newField,
                        onValueChange = {
                            newField = it
                        },
                        label = { Text("Field Name") }
                    )
                } else {
                    // Mostrar el atributo seleccionado
                    Text("Selected Attribute: $selectedAttribute")
                }

                // Botones dentro del contenido del cuadro de diálogo
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = {
                            onDismiss()
                        }
                    ) {
                        Text("Cancel")
                    }
                    TextButton(
                        onClick = {
                            if (selectedAttribute != null) {
                                onAddField("$selectedAttribute - $newField")
                                onDismiss()
                            } else if (newField.isNotBlank()) {
                                onAddField(newField)
                                onDismiss()
                            }
                        }
                    ) {
                        Text("Add")
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ClientFormPreview() {
    ClientForm()
}