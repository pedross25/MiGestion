package com.example.migestion.ui.screens.selectproductsscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.migestion.R
import com.example.migestion.model.Product
import com.example.migestion.ui.screens.createinvoicescreen.AlbaranInvoice
import com.example.migestion.ui.screens.createinvoicescreen.Bottom
import com.example.migestion.ui.theme.BlueInvoice
import com.example.migestion.ui.theme.TextGray

@Composable
fun SelectProduct() {
    Column {
        Header()
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.7f),
            color = Color(0xFF45697B).copy(alpha = 0.15F)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                SelectProducts()
                Spacer(modifier = Modifier.padding(8.dp))
                ProductList()
            }
        }
        BottomSelectProduct()
    }


}

@Composable
fun Header() {
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
                    //navController.popBackStack()
                }
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = "Seleccionar productos", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun SelectProducts() {
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
            Column() {
                Text(
                    text = "Producto",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextGray.copy(alpha = 0.70f)
                )
                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = "Patinete Xiaomi Elec...",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = "Mano de obra",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            }
            Column {
                Text(
                    text = "Cant",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextGray.copy(alpha = 0.70f)
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = "1",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = "1,5",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            }
            Column {
                Text(
                    text = "Precio",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextGray.copy(alpha = 0.70f)
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = "195,50 €",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = "25 €",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            }
            Column {
                Text(
                    text = "Total",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextGray.copy(alpha = 0.70f)
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = "195,50 €",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = "50 €",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Composable
fun BottomSelectProduct() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp, end = 16.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Button(/*colors = , */onClick = { /*TODO*/ }) {
            Text(text = "Continuar", color = Color.White)
        }
    }
}

@Composable
fun ProductList() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(10) { product ->
            ProductCard()

        }
    }
}

@Composable
fun ProductCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle click on the card if needed */ },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        /*elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )*/
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
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
                text = "Patinete Xiaomi Electric Scooter 4",
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "195,50 €",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add to Cart",
                    modifier = Modifier.clickable { }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SelectProductScreenPreview() {
    SelectProduct()
}