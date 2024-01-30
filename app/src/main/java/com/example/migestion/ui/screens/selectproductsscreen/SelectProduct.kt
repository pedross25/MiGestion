package com.example.migestion.ui.screens.selectproductsscreen

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.migestion.R
import com.example.migestion.model.Product
import com.example.migestion.model.Response
import com.example.migestion.ui.components.MinimalDialog2
import com.example.migestion.ui.components.ProgressBar
import com.example.migestion.ui.theme.BlueCobrado

@Composable
fun SelectProduct(
    idInvoice: Int,
    viewModel: SelectProductViewModel = hiltViewModel(),
    navController: NavController
) {

    viewModel.setInvoice(idInvoice)

    var openDialog = viewModel.openDialog
    viewModel.selectProduct?.let {
        MinimalDialog2(
            it,
            openDialog,
            onDismissRequest = { viewModel.closeDialog() },
            viewModel = viewModel
        )
    }

    Column {
        Header(navController)
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.7f),
            color = Color(0xFF45697B).copy(alpha = 0.15F)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                ProductList(idInvoice)
            }
        }
        BottomSelectProduct(navController = navController)
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
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier
                .padding(4.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = "Seleccionar productos", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun BottomSelectProduct(
    viewModel: SelectProductViewModel = hiltViewModel(),
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp, end = 16.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Button(/*colors = , */onClick = {
            viewModel.finishSelection()
            navController.popBackStack()
        }) {
            Text(text = "Continuar", color = Color.White)
        }
    }
}

@Composable
fun ProductList(idInvoice: Int, viewModel: SelectProductViewModel = hiltViewModel()) {
    val productsResponse = viewModel.productsResponse

    val combinedList = mutableListOf<Product>().apply {
        addAll(viewModel.listProduct)
        //add(DividerItem) // Un objeto que representa la línea divisoria
        if (productsResponse is Response.Success) {
            addAll(productsResponse.data)
        }
    }

    when (productsResponse) {
        //TODO Caso de fallo
        is Response.Failure -> {}
        Response.Loading -> ProgressBar()
        is Response.Success -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(combinedList) {
                    ProductCard(product = it, idInvoice = idInvoice)
                }
            }
        }
    }
}

@Composable
fun ProductCard(
    product: Product,
    idInvoice: Int,
    viewModel: SelectProductViewModel = hiltViewModel()
) {
    var addedUnits by remember { mutableStateOf(0) }
    LaunchedEffect(viewModel.listProduct) {
        addedUnits = viewModel.listProduct.find { it.id == product.id }?.amount ?: 0
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .clickable {
                viewModel.openDialog(product = product)
            },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            if (addedUnits > 0) {
                Box(
                    modifier = Modifier
                        /*.padding(8.dp)*/
                        .size(32.dp)
                        .align(Alignment.End)
                        .clip(CircleShape)
                        .background(BlueCobrado),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = addedUnits.toString(),
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }
            val painter = painterResource(id = R.drawable.patinete)
            Image(
                painter = painter, contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.name,
                maxLines = 2,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = product.price.toString() + "€",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )
                /*Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add to Cart",
                    modifier = Modifier.clickable { }
                )*/
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SelectProductScreenPreview() {
    //SelectProduct()
}