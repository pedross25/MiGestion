package com.example.migestion.ui.screens.customerscreen

import androidx.annotation.DrawableRes
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
import androidx.compose.material.icons.filled.Edit
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
import com.example.migestion.ui.components.BarraBusqueda

data class CustomerEntity(val name: String, val correo: String)
data class MenuItem(val text: String, @DrawableRes val icon: Int)

@Composable
fun CustomerScreen() {

    val customerList = listOf(
        CustomerEntity("John Doe", "john.doe@example.com"),
        CustomerEntity("Jane Smith", "jane.smith@example.com"),
        CustomerEntity("Alice Johnson", "alice.johnson@example.com"),
        CustomerEntity("Bob Williams", "bob.williams@example.com"),
        CustomerEntity("Eva Davis", "eva.davis@example.com"),
        CustomerEntity("David Brown", "david.brown@example.com"),
        CustomerEntity("Emma Taylor", "emma.taylor@example.com"),
        CustomerEntity("Michael Miller", "michael.miller@example.com"),
        CustomerEntity("Olivia Davis", "olivia.davis@example.com"),
        CustomerEntity("William Jones", "william.jones@example.com"),
        CustomerEntity("Sophia Wilson", "sophia.wilson@example.com"),
        CustomerEntity("Noah Davis", "noah.davis@example.com"),
        CustomerEntity("Ava Robinson", "ava.robinson@example.com"),
        CustomerEntity("Liam White", "liam.white@example.com"),
        CustomerEntity("Isabella Thompson", "isabella.thompson@example.com"),
        CustomerEntity("Mason Harris", "mason.harris@example.com"),
        CustomerEntity("Olivia Davis", "olivia.davis@example.com"),
        CustomerEntity("Emma Robinson", "emma.robinson@example.com"),
        CustomerEntity("Lucas Hall", "lucas.hall@example.com"),
        CustomerEntity("Ava Davis", "ava.davis@example.com")
    )

    var showClientForm by remember { mutableStateOf(false) }

    if (showClientForm) {
        ClientForm()
    }

    Column {
        BarraBusqueda()
        CustomerList(customerList) {}
    }


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomerList(
    customers: List<CustomerEntity>,
    onItemClick: (String) -> Unit,
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
    var selectedCustomer by remember { mutableStateOf<CustomerEntity?>(null) }

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
            val groupedClientes: Map<String, List<CustomerEntity>> = customers.groupBy {
                (it.name.firstOrNull()?.uppercaseChar() ?: "Sin nombre").toString()
            }

            LazyColumn {
                groupedClientes.forEach { (letra, clientesConLetra) ->
                    stickyHeader {
                        // Sticky header que muestra la letra
                        Text(
                            text = letra.toString(),
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
                            // Actualizar el cliente seleccionado
                            selectedCustomer = customer

                            // Calcular la posición del menú emergente


                            // Mostrar el menú emergente
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

       /*         DropdownMenu(
                    expanded = isMenuVisible,
                    onDismissRequest = { isMenuVisible = false },
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Gray.copy(alpha = 0.5f))
                        .heightIn(max = menuHeight)
                        .widthIn(max = menuWidth)
                ) {
                    items.forEach { menuItem ->
                        DropdownMenuItem(onClick = {
                            isMenuVisible = false
                            // Handle the selected item here
                            selectedIndex = items.indexOf(menuItem)
                        }) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = menuItem.icon),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(menuItem.text)
                            }
                        }
                    }
                }*/
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
fun CustomerItem(customer: CustomerEntity, onItemClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onItemClick)
    ) {
        Text(
            text = customer.name,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        /*Text(
            text = "Email: ${customer.correo}",
            style = MaterialTheme.typography.bodyMedium
        )*/
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ClientForm() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var optionalFields by remember { mutableStateOf(listOf("Field 1", "Field 2", "Field 3")) }
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
                Text(text = field, modifier = Modifier.weight(1f))

                IconButton(
                    onClick = {
                        // Editar campo opcional
                        selectedFieldIndex = index
                        isAddingField = true
                    }
                ) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                }

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
    val predefinedAttributes = listOf("Attribute 1", "Attribute 2", "Attribute 3")

    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.background(Color.Gray),
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