package com.example.migestion.ui.screens.createproductscreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun CreateProduct(navController: NavController) {
    Column {
        Header(navController)
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.7f),
            color = Color(0xFF45697B).copy(alpha = 0.15F)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                FieldsProduct()
                ExposedDropdownMenuSample()
                Spacer(modifier = Modifier.padding(8.dp))
                ImageSelectorScreen()
            }
        }
        BottomCreateProduct()
    }
}

@Composable
fun Header(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Default.Close,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier
                .padding(4.dp)
                .clickable {
                    navController.popBackStack()
                })
        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = "Crear producto", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FieldsProduct(viewModel: CreateProductViewModel = hiltViewModel()) {
    //var name by remember { mutableStateOf("") }
    //var description by remember { mutableStateOf("") }
    //var cantidad by remember { mutableStateOf("") }
    //var precio by remember { mutableStateOf("") }

    var name = viewModel.productName
    var description = viewModel.productDescription
    var cantidad = viewModel.productQuantity
    var precio = viewModel.productPrice


    TextField(value = name.value, onValueChange = {
        viewModel.setProductName(it)
    }, label = { Text(text = "Nombre") }, modifier = Modifier
        .border(
            width = 1.dp, color = Color.Black, shape = RoundedCornerShape(20.dp)
        )
        .fillMaxWidth(), colors = TextFieldDefaults.textFieldColors(
        cursorColor = Color.Black,
        unfocusedIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent
    ), shape = RoundedCornerShape(8.dp)
    )
    Spacer(modifier = Modifier.padding(8.dp))
    TextField(value = description.value, onValueChange = {
        viewModel.setProductDescription(it)
    }, label = { Text(text = "Descripción") }, modifier = Modifier
        .border(
            width = 1.dp, color = Color.Black, shape = RoundedCornerShape(20.dp)
        )
        .wrapContentHeight()
        .fillMaxWidth(), colors = TextFieldDefaults.textFieldColors(
        cursorColor = Color.Black,
        unfocusedIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent
    ), shape = RoundedCornerShape(8.dp)
    )
    Spacer(modifier = Modifier.padding(8.dp))
    TextField(
        value = cantidad.value,
        onValueChange = {
            viewModel.setProductQuantity(it)
        },
        label = { Text(text = "Cantidad") },
        modifier = Modifier
            .border(
                width = 1.dp, color = Color.Black, shape = RoundedCornerShape(20.dp)
            )
            .wrapContentHeight()
            .fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = Color.Black,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
    Spacer(modifier = Modifier.padding(8.dp))
    TextField(
        value = precio.value,
        onValueChange = {
            viewModel.setProductPrice(it)
        },
        label = { Text(text = "Precio") },
        modifier = Modifier
            .border(
                width = 1.dp, color = Color.Black, shape = RoundedCornerShape(20.dp)
            )
            .wrapContentHeight()
            .fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = Color.Black,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
    Spacer(modifier = Modifier.padding(8.dp))
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageSelectorScreen(viewModel: CreateProductViewModel = hiltViewModel()) {

    val multiplePhotopicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { selectedImages ->
            viewModel.addImages(selectedImages)
        }
    )
    val haptics = LocalHapticFeedback.current

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        var contextMenuPhotoId by rememberSaveable { mutableStateOf<Uri?>(null) }


        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            item {
                Button(
                    onClick = {
                        multiplePhotopicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .height(120.dp)
                        .width(120.dp)
                ) {
                    Text(
                        text = "Añadir imágenes",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
            items(viewModel.productPhotos.value) { uri ->
                Box(
                    modifier = Modifier.combinedClickable(
                        onClick = {  },
                        onLongClick = {
                            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                            contextMenuPhotoId = uri
                        },
                        onLongClickLabel = "HOLAAA"
                    )
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(uri)
                            .crossfade(enable = true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(120.dp)
                            .width(120.dp)
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(16.dp))
                    )
                }
            }
        }
        if (contextMenuPhotoId != null) {
            PhotoActionsSheet(
                onDismissSheet = { contextMenuPhotoId = null }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PhotoActionsSheet(
    /*@Suppress("UNUSED_PARAMETER") photo: Photo,*/
    onDismissSheet: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissSheet
    ) {
        ListItem(
            headlineContent = { Text("Add to album") },
            leadingContent = { Icon(Icons.Default.Add, null) }
        )
        ListItem(
            headlineContent = { Text("Add to favorites") },
            leadingContent = { Icon(Icons.Default.FavoriteBorder, null) }
        )
        ListItem(
            headlineContent = { Text("Share") },
            leadingContent = { Icon(Icons.Default.Share, null) }
        )
        ListItem(
            headlineContent = { Text("Remove") },
            leadingContent = { Icon(Icons.Default.Delete, null) }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuSample() {
    val options = listOf("General", "Food", "Bill Payment", "Recharges", "Outing", "Other")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = {},
            label = { Text("Categoria") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        selectedOptionText = selectionOption
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Composable
fun BottomCreateProduct(
    viewModel: CreateProductViewModel = hiltViewModel()
    /*navController: NavController*/
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp, end = 16.dp)
    ) {
        Button(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = {
                viewModel.saveProduct()
            }
        ) {
            Text(text = "Crear", color = Color.White)
        }
    }
}


/*
@Preview(showBackground = true)
@Composable
fun CreateProductScreenPreview() {
    CreateProduct()
}*/
