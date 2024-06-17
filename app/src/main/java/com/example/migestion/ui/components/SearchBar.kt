package com.example.migestion.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraBusqueda() {
    Surface(
        modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.background
    ) {
        var isSearchActive by remember { mutableStateOf(false) }
        var searchQuery by remember { mutableStateOf("") }
        var padding by remember { mutableIntStateOf(16) }

        SearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            onSearch = {},
            active = isSearchActive,
            onActiveChange = { isSearchActive = it },
            modifier = Modifier
                .wrapContentHeight()
                .padding(start = padding.dp, end = padding.dp),
            placeholder = { Text(text = "Buscar") },
            leadingIcon = {
                if (isSearchActive) {
                    // Mostrar la flecha de retroceso cuando la búsqueda está activa
                    IconButton(onClick = { isSearchActive = false }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                    }
                } else {
                    // Mostrar el botón de menú cuando la búsqueda no está activa
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "")
                    }
                }
            },
            trailingIcon = {
                if (isSearchActive && searchQuery.isNotEmpty()) {
                    IconButton(onClick = {
                        isSearchActive = false
                        searchQuery = ""
                    }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "")
                    }
                }
                if (!isSearchActive) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "")
                    }
                }

            },
        ) {
            padding = if (isSearchActive) {
                0
            } else {
                16
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnlySearchBar() {
    var isSearchActive by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var padding by remember { mutableIntStateOf(16) }

    SearchBar(query = searchQuery,
        onQueryChange = { searchQuery = it },
        onSearch = {},
        shape = RoundedCornerShape(12.dp),
        colors = SearchBarDefaults.colors(containerColor = Color.White),
        active = isSearchActive,
        onActiveChange = { isSearchActive = it },
        modifier = Modifier
            .wrapContentHeight()
            .padding(start = padding.dp, end = padding.dp),
        placeholder = { Text(text = "Buscar") },
        leadingIcon = {
            if (isSearchActive) {
                // Mostrar la flecha de retroceso cuando la búsqueda está activa
                IconButton(onClick = { isSearchActive = false }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                }
            } else {
                // Mostrar el botón de menú cuando la búsqueda no está activa
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "")
                }
            }
        },
        trailingIcon = {
            if (isSearchActive && searchQuery.isNotEmpty()) {
                IconButton(onClick = {
                    isSearchActive = false
                    searchQuery = ""
                }) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = "")
                }
            }
            if (!isSearchActive) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "")
                }
            }

        }) {}
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NewSearchBar(
    onQueryChange: (String) -> Unit,
    onArrowIconClick: () -> Unit,
    onSearch: (text: String) -> Unit,
    onClearIconClick: () -> Unit
) {
    var text by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    TextField(
        value = text,
        onValueChange = {
            text = it
            onQueryChange(it)
        },
        leadingIcon = {
            IconButton(onClick = { onArrowIconClick() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Right Icon")
            }
        },
        trailingIcon = {
            if (text.isNotEmpty()) {
                IconButton(onClick = {
                    text = ""
                    onClearIconClick()
                    keyboardController?.show()
                }) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = "Right Icon")
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = Color.Black,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        placeholder = {
            Text(text = "Buscar")
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(text)
                keyboardController?.hide()
            }
        )
    )
}

@Preview
@Composable
fun PreviewCustomTextField() {
}