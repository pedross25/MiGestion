package com.example.migestion.ui.screens.createcustomerscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.migestion.ui.screens.customerscreen.AddOptionalFieldDialog


@Composable
fun CreateCustomerScreen(navController: NavController) {

    Column {
        CustomerHeader(navController)
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.7f),
            color = Color(0xFF45697B).copy(alpha = 0.15F)
        ) {
            ClientForm1()
        }
        //BottomCreateCustomer()

    }

}

@Composable
fun CustomerHeader(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier
                .padding(4.dp)
                //.fillMaxSize()
                .clickable {
                    navController.popBackStack()
                }
        )
        Spacer(modifier = Modifier.padding(8.dp))


        Text(
            text = "Crear contacto",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier
                .padding(4.dp)
                //.fillMaxSize()
                .clickable {
                    navController.popBackStack()
                }
        )

    }
}

@Composable
fun ClientForm1() {
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
            .padding(32.dp)
            .fillMaxSize()
    ) {
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
                        optionalFields = optionalFields.toMutableList().apply {
                            removeAt(index)
                        }
                    }
                ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = {
                    isAddingField = true
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }

        if (isAddingField) {
            AddOptionalFieldDialog(
                onDismiss = {
                    selectedFieldIndex = -1
                    keyboardController?.hide()
                    isAddingField = false
                },
                onAddField = { newField ->
                    optionalFields = if (selectedFieldIndex != -1) {
                        optionalFields.toMutableList().apply {
                            set(selectedFieldIndex, newField)
                        }
                    } else {
                        optionalFields + newField
                    }
                },
                initialField = if (selectedFieldIndex != -1) optionalFields[selectedFieldIndex] else ""
            )
        }
    }
}