package com.example.migestion.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.migestion.data.network.HttpRoutes
import com.example.migestion.model.Product
import com.example.migestion.ui.screens.selectproductsscreen.SelectProductViewModel
import com.example.migestion.ui.theme.BlueCobrado

@Composable
fun ProductCard(
    product: Product,
    onClick: () -> Unit
) {
    var addedUnits by remember { mutableStateOf(0) }/*LaunchedEffect(viewModel.listProduct) {
        addedUnits = viewModel.listProduct.find { it.id == product.id }?.amount ?: 0
    }*/

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .clickable {
                onClick()
            }, shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ), elevation = CardDefaults.cardElevation(
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
                        .size(32.dp)
                        .align(Alignment.End)
                        .clip(CircleShape)
                        .background(BlueCobrado), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = addedUnits.toString(), color = Color.White, fontSize = 14.sp
                    )
                }
            }
            AsyncImage(
                model = HttpRoutes.Image.GET + product.id + "/0",
                contentDescription = null,
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
            }
        }
    }
}