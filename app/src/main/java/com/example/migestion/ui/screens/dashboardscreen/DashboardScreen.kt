package com.example.migestion.ui.screens.dashboardscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.migestion.R
import com.example.migestion.ui.components.OnlySearchBar
import com.example.migestion.ui.navigation.BottomNavItem
import com.example.migestion.ui.theme.AzulItem2Dash
import com.example.migestion.ui.theme.RojoGastos


@Composable
fun DashboardScreen(padding: PaddingValues) {
    Surface(color = Color(0xFFF5F7FA)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Box(
                Modifier.background(
                    color = Color(0xFFDCE8F5), shape = RoundedCornerShape(0.dp, 0.dp, 12.dp, 12.dp)
                )
            ) {
                Column(
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    OnlySearchBar()
                    Spacer(modifier = Modifier.padding(8.dp))
                    //Header(title = "Dashboard")
                    TextWithDropdownMenu()
                }
            }
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = "Widgets", modifier = Modifier.weight(1f))
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = "Expand",
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                ItemDashboard(
                    R.drawable.subtract,
                    color = 0xFF5DB075,
                    primaryText = "Ingresos",
                    secondText = "6 facturas",
                    total = "3578.00 €"
                )
                ItemDashboard(
                    R.drawable.subtract_2,
                    color = 0xFFDF5F5F,
                    primaryText = "Gastos",
                    secondText = "4 gastos",
                    total = "2357.24 €"
                )
                //Item2Dashboard(modifier = Modifier.fillMaxWidth())
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
                Box(
                    modifier = Modifier
                        .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                ) {
                    Movements()
                }
                SquareWidget()


            }
        }
    }
}

@Composable
fun ItemDashboard(
    idResource: Int,
    color: Long,
    modifier: Modifier = Modifier,
    primaryText: String,
    secondText: String,
    total: String
) {
    Box(
        modifier = modifier
            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val painter = painterResource(id = idResource)
            Icon(
                painter = painter,
                contentDescription = "subtract icon",/*tint = Color(0XFF446388),*/
                tint = Color(color),
                modifier = Modifier.size(50.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = primaryText,
                    color = Color(0xFF283B51),
                )
                Text(
                    text = secondText,
                    color = Color(0xFFA3B8D1),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )

            }

            Text(
                text = total,
                color = Color(0xFF283B51),
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp
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
                text = "Pendiente", color = Color.White
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

@Composable
fun TextWithDropdownMenu() {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Día") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(horizontalArrangement = Arrangement.End) {
                Spacer(modifier = Modifier.weight(1f))
            }

        }
        Row {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Balance",
                    color = Color(0XFF446388),
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "0.00 €",
                    fontSize = 42.sp,
                    color = Color(0xFF283B51),
                    fontWeight = FontWeight.SemiBold
                )

            }
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = "Hoy", color = Color.Black)
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            Icons.Filled.ArrowDropDown,
                            contentDescription = "Expand",
                            tint = Color.Black
                        )
                    }
                }
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
        Text(text = "Ingresos")
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
        modifier = modifier.padding(horizontal = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .background(Color(0xFFFBE7D0), shape = RoundedCornerShape(4.dp))
                .height(130.dp)
                .width(24.dp)

        ) {
            Box(
                modifier = Modifier
                    .background(Color.White)
                    .height(expensePercentage.dp)
                    .width(24.dp)
            )
        }
        Text(text = monthData.month, textAlign = TextAlign.Center, color = Color(0xFF283B51))
    }
}

@Composable
fun SquareWidget() {
    Box(
        modifier = Modifier.background(Color.White, shape = RoundedCornerShape(12.dp))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = "Próximos movimientos")
            Row {
                Column {
                    Text(text = "")

                }
            }
        }
    }
}

data class Movimiento(
    val tipoMovimiento: String,
    val cantidad: Double,
    val nombre: String,
    val fecha: String,
    val total: Double
)


@Composable
fun Movements() {
    val movimientos = listOf(
        Movimiento("Factura", 100.0, "Proveedor A", "2024-02-20", 500.0),
        Movimiento("Gasto", 50.0, "Gasto 1", "2024-02-21", -50.0),
        Movimiento("Factura", 200.0, "Proveedor B", "2024-02-22", 700.0),
        Movimiento("Gasto", 30.0, "Gasto 2", "2024-02-23", -80.0),
        Movimiento("Factura", 150.0, "Proveedor C", "2024-02-24", 850.0)
    )

    Column(modifier = Modifier.padding(8.dp)) {
        for (i in 1..3) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .clickable { }) {
                Column(
                    modifier = Modifier
                        .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
                ) {

                    Text(
                        text = "Factura #0001 Revestimientos Mar.",
                        //color = Color(0xFFA3B8D1),
                        //fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "+ 2451.56 €", color = Color(0xFF686868))
                        Text(text = "4965.56 €", color = Color(0xFF8EB584))
                    }
                }
            }
        }
    }
}

@Composable
fun DragAndDropScreen() {
    var items by remember { mutableStateOf(mutableListOf("Widget 1", "Widget 2", "Widget 3")) }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(items.size) { index ->
                DraggableItem(text = items[index], onDragEnd = { newIndex ->
                    val item = items.removeAt(index)
                    items.add(newIndex, item)
                })
            }
        }
    }
}

@Composable
fun DraggableItem(text: String, onDragEnd: (Int) -> Unit) {
    var draggedIndex by remember { mutableStateOf(-1) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    onDragEnd(dragAmount.y.toInt() - 1)

                    /*offsetX = box1.toFloat()
                    offsetY += dragAmount.y*/

                }
            }
    ) {
        Text(text = text, modifier = Modifier.padding(16.dp))
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
    Movements()

    //DashboardScreen()
}

internal val LocalDragTargetInfo = compositionLocalOf { DragTargetInfo() }

internal class DragTargetInfo {
    var isDragging: Boolean? by mutableStateOf(false)
    var dragPosition by mutableStateOf(Offset.Zero)
    var dragOffset by mutableStateOf(Offset.Zero)
    var draggableComposable by mutableStateOf<(@Composable () -> Unit)?>(null)
    var dataToDrop by mutableStateOf<Any?>(null)
    var itemDropped: Boolean by mutableStateOf(false)
    var absolutePositionX: Float by mutableFloatStateOf(0F)
    var absolutePositionY: Float by mutableStateOf(0F)
}

@Composable
fun LongPressDraggable(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val state = remember { DragTargetInfo() }

    CompositionLocalProvider(
        LocalDragTargetInfo provides state
    ) {
        Box(modifier = modifier.fillMaxSize())
        {
            content()
            if (state.isDragging == true) {
                var targetSize by remember {
                    mutableStateOf(IntSize.Zero)
                }
                Box(modifier = Modifier
                    .graphicsLayer {
                        val offset = (state.dragPosition + state.dragOffset)
                        scaleX = 1.5f
                        scaleY = 1.5f
                        alpha = if (targetSize == IntSize.Zero) 0f else .9f
                        translationX = offset.x.minus(targetSize.width / 2)
                        translationY = offset.y.minus(targetSize.height / 2)
                    }
                    .onGloballyPositioned {
                        targetSize = it.size
                        it.let { coordinates ->
                            state.absolutePositionX = coordinates.positionInRoot().x
                            state.absolutePositionY = coordinates.positionInRoot().y
                        }
                    }
                ) {
                    state.draggableComposable?.invoke()
                }
            }
        }
    }
}