package com.example.migestion.ui.screens.invoicescreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.migestion.ui.components.BarraBusqueda
import com.example.migestion.ui.theme.BlueCobrado
import com.example.migestion.ui.theme.BlueInvoice
import com.example.migestion.ui.theme.NaranjaPendiente
import kotlinx.coroutines.launch

data class Invoice(
    val invoiceNumber: String,
    val clientName: String,
    val amount: String,
    val isPaid: Boolean,
    val isOpenedByClient: Boolean
)


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InvoiceScreen() {

    val tabs = listOf("Facturas", "Albaranes", "Presupuestos")

    val pagerState = rememberPagerState(pageCount = {tabs.size})
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }



    val invoices = listOf(
        Invoice("12345", "John Doe", "$100.00", true, false),
        Invoice("67890", "Jane Smith", "$50.00", false, true),
        Invoice("13579", "Alice Johnson", "$75.00", false, true),
        Invoice("13579", "Alice Johnson", "$75.00", true, true),
        Invoice("13579", "Alice Johnson", "$75.00", true, true),
        Invoice("13579", "Alice Johnson", "$75.00", true, true),
        Invoice("13579", "Alice Johnson", "$75.00", false, true),
        Invoice("13579", "Alice Johnson", "$75.00", false, true),
        Invoice("13579", "Alice Johnson", "$75.00", true, true),
        Invoice("13579", "Alice Johnson", "$75.00", true, true),
        Invoice("13579", "Alice Johnson", "$75.00", true, true),
        Invoice("13579", "Alice Johnson", "$75.00", false, true),
        Invoice("13579", "Alice Johnson", "$75.00", true, true),
        Invoice("13579", "Alice Johnson", "$75.00", false, true),
        Invoice("13579", "Alice Johnson", "$75.00", true, true),
    )

    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxWidth()) {
        BarraBusqueda()
        TabRow(selectedTabIndex = selectedTabIndex.value) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = selectedTabIndex.value == index,
                    /*onClick = { tabIndex = index }*/
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                InvoiceList(invoices = invoices)
            }
        }
    }
}

@Composable
fun InvoiceList(invoices: List<Invoice>) {
    LazyColumn {
        items(invoices) { invoice ->
            Column {
                InvoiceCard(invoice = invoice)
                Divider(color = Color.Gray, thickness = 1.dp)
            }
        }
    }
}

@Composable
fun InvoiceCard(invoice: Invoice) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Column(
                modifier = Modifier
                    .wrapContentWidth()
            ) {
                Text(text = "FACTURA${invoice.invoiceNumber}", color = BlueInvoice, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = invoice.clientName, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = invoice.amount, fontWeight = FontWeight.SemiBold)
            }
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center

            ) {
                RoundedBlueText(invoice.isPaid)
            }
        }

    }
}



@Composable
fun RoundedBlueText(cobrado: Boolean) {
    val backgroundColor = if (cobrado) BlueCobrado else NaranjaPendiente
    val textToShow = if (cobrado) "Cobrado" else "Pendiente"

    Box(
        modifier = Modifier
            .wrapContentSize()
            .background(backgroundColor, shape = RoundedCornerShape(24.dp))
            .padding(4.dp)
    ) {
        Text(
            text = textToShow,
            color = Color.White,
            modifier = Modifier
                .padding(4.dp),
            fontWeight = FontWeight.SemiBold
        )
    }
}


@Preview(showBackground = true)
@Composable
fun InvoiceScreenPreview() {
    InvoiceScreen()
}