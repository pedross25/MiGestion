package com.example.migestion.ui.screens.dashboardscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.migestion.R
import com.example.migestion.ui.components.Header
import com.example.migestion.ui.theme.AzulButtonDash
import com.example.migestion.ui.theme.AzulDashboard
import com.example.migestion.ui.theme.AzulItem2Dash
import com.example.migestion.ui.theme.AzulItemDash
import com.example.migestion.ui.theme.BlueCobrado
import com.example.migestion.ui.theme.RojoGastos

@Composable
fun DashboardScreen() {
    Scaffold(topBar = {
        Header(title = "Dashboard")
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFF45697B).copy(alpha = 0.08F))
                .padding(it)
                .padding(start = 12.dp, end = 12.dp)
            , verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = AzulDashboard, shape = RoundedCornerShape(24.dp)
                    )
                    .wrapContentHeight()
            ) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row() {
                        val blueUnselect = BlueCobrado.copy(alpha = 0.63f)

                        Button(
                            onClick = {

                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AzulButtonDash.copy(
                                    alpha = 0.9f
                                )
                            ),
                            contentPadding = PaddingValues(8.dp),
                            shape = RoundedCornerShape(24.dp),
                            modifier = Modifier
                                .padding(4.dp)
                                .height(35.dp)
                        ) {
                            Text(text = "Hoy")
                        }
                        Button(
                            onClick = {

                            },
                            colors = ButtonDefaults.buttonColors(containerColor = blueUnselect),
                            contentPadding = PaddingValues(8.dp),
                            shape = RoundedCornerShape(24.dp),
                            modifier = Modifier
                                .padding(4.dp)
                                .height(35.dp)
                        ) {
                            Text(text = "Semana")
                        }
                        Button(
                            onClick = {},
                            contentPadding = PaddingValues(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = blueUnselect),
                            shape = RoundedCornerShape(24.dp),
                            modifier = Modifier
                                .padding(4.dp)
                                .height(35.dp)
                        ) {
                            Text(text = "Mes")
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        ItemDashboard(modifier = Modifier.weight(1f))
                        ItemDashboard(modifier = Modifier.weight(1f))
                    }
                    Item2Dashboard(modifier = Modifier.fillMaxWidth())
                }
            }
            val monthDataList = listOf(
                MonthData("Ene", 2000f, 800f),
                MonthData("Feb", 1500f, 1600f),
                MonthData("Mar", 2200f, 1000f),
                MonthData("Abr", 1800f, 1500f),
                MonthData("May", 2500f, 1200f),
                MonthData("Jun", 1900f, 1800f),
                MonthData("Jul", 2300f, 1300f),
                MonthData("Ago", 2000f, 2000f),
                MonthData("Sep", 2400f, 1100f),
                MonthData("Oct", 1700f, 2100f),
                MonthData("Nov", 2600f, 1400f),
                MonthData("Dic", 2100f, 2500f)
            )
            BarChart(monthDataList)

        }
    }
}

@Composable
fun ItemDashboard(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(color = AzulItemDash, shape = RoundedCornerShape(24.dp))
            .padding(12.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row {
                Text(
                    text = "Facturación",
                    color = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.weight(1f)
                )
                val painter = painterResource(id = R.drawable.subtract)
                Icon(painter = painter, contentDescription = "subtract icon", tint = Color.White)
            }
            Text(
                text = "6 facturas",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
            Text(
                text = "3578 €", color = Color.White, fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp
            )
        }
    }
}

@Composable
fun Item2Dashboard(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(color = AzulItem2Dash, shape = RoundedCornerShape(24.dp))
            .padding(12.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

            Text(
                text = "Pendiente",
                color = Color.White
            )

            Row {
                Text(
                    text = "Gastos",
                    color = Color.White.copy(alpha = 0.7f),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "- 2478 €",
                    color = RojoGastos,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
            }
            Row {
                Text(
                    text = "Ingresos",
                    color = Color.White.copy(alpha = 0.7f),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "+ 4878 €",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
            }
            Row {
                Text(
                    text = "TOTAL",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "+ 2400 €",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
            }
        }
    }
}

data class MonthData(val month: String, val income: Float, val expenses: Float)

@Composable
fun BarChart(monthDataList: List<MonthData>) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var barWidth = ((screenWidth - 32.dp) / monthDataList.size)
    if (barWidth < 50.dp) {
        barWidth = 50.dp
    }

    Column(
        modifier = Modifier
            .background(color = Color.White, shape = RoundedCornerShape(20.dp))
            .padding(12.dp)
    ) {
        Text(text = "Ratio Gastos/Ingresos")
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(monthDataList.size) { index ->
                val monthData = monthDataList[index]
                Bar(monthData, modifier = Modifier.width(barWidth))
            }
        }
    }
}

@Composable
fun Bar(monthData: MonthData, modifier: Modifier = Modifier) {
    val total = monthData.income + monthData.expenses
    val incomePercentage = monthData.income / total * 100
    val expensePercentage = monthData.expenses / total * 130

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(horizontal = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .background(Color.Green, shape = RoundedCornerShape(24.dp))
                .height(130.dp)
                .width(16.dp)

        ) {
            Box(
                modifier = Modifier
                    .background(Color.Red, shape = RoundedCornerShape(24.dp))
                    .height(expensePercentage.dp)
                    .width(16.dp)
            )
        }

        Text(text = monthData.month, textAlign = TextAlign.Center)
    }
}

@Preview(showBackground = true)
@Composable
fun ItemDashboardView() {
    val monthDataList = listOf(
        MonthData("En", 1500f, 800f),
        MonthData("Fe", 1000f, 1200f),
        MonthData("Ma", 2000f, 600f),
        MonthData("Ab", 1800f, 900f),
        MonthData("Ma", 2200f, 1000f),
        MonthData("Ju", 2500f, 700f)
    )

    //BarChart(monthDataList)
    DashboardScreen()
}