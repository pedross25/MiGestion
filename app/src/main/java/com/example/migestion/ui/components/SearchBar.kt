package com.example.migestion.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.migestion.ui.theme.Gray
import com.example.migestion.ui.theme.MiGestionTheme
import com.google.android.material.search.SearchBar

/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraBusqueda(modifier: Modifier) {

    var searchText by remember { mutableStateOf("") }

    SearchBar(
        query = searchText,
        onQueryChange = { searchText = it },
        onSearch = {},
        active = true,
        placeholder = {
            Text(text = "Buscar")
        },
        onActiveChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
            )
        },
        modifier = modifier) {

    }
}*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraBusqueda() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.background
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
