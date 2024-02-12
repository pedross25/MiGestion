package com.example.migestion.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.migestion.data.network.HttpRoutes
import com.example.migestion.model.Product
import com.example.migestion.ui.theme.NaranjaPendiente

@Composable
fun ProductListView(product: Product) {
        Row(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Column {
                Text(
                    text = product.name,
                    maxLines = 2,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = product.price.toString() + "€",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                )
                Text(
                    text = "Cantidad: " + product.amount,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                )
                Text(
                    text = "Descuento: 0 %",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = (product.price * product.amount).toString() + " €",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    color = NaranjaPendiente
                )

            }
            Column {
                val idImage: Int = product.parentId ?: product.id
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(HttpRoutes.Image.GET + idImage + "/0")
                        .crossfade(enable = true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(150.dp)
                        .widthIn(30.dp)
       /*                 .width(120.dp)*/
                        //.fillMaxWidth()
                        .clip(shape = RoundedCornerShape(16.dp))
                )
            }
        }
}

@Preview(showBackground = true)
@Composable
fun ProductView() {
    val product = Product(
        id = 1,
        name = "Patinete Xiaomi Electric Scooter 4",
        amount = 10,
        price = 29.99,
        category = "Electrónicos",
        createdAt = "2024-01-29",
        template = false,
        description = "Descripción del producto de ejemplo",
        invoice = null
    )
    ProductListView(product)
}