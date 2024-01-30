package com.example.migestion.ui.screens.invoicescreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.migestion.data.db.SelectInvoiceWithCustomer
import com.example.migestion.model.Response
import com.example.migestion.ui.components.BarraBusqueda
import com.example.migestion.ui.components.ProgressBar
import com.example.migestion.ui.theme.BlueCobrado
import com.example.migestion.ui.theme.BlueInvoice
import com.example.migestion.ui.theme.NaranjaPendiente
import kotlinx.coroutines.launch


@Composable
fun Invoices(
    viewModel: InvoiceViewModel = hiltViewModel(),
    invoicesContent: @Composable (invoices: List<SelectInvoiceWithCustomer>) -> Unit
) {
    //val lifecycleOwner = LocalLifecycleOwner.current
    //val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    /*LaunchedEffect(lifecycleState) {

        when (lifecycleState) {
            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.INITIALIZED -> {}
            Lifecycle.State.CREATED -> {}
            Lifecycle.State.STARTED -> {}
            Lifecycle.State.RESUMED -> {
                viewModel.onResume()
            }
        }
    }*/

    when (val invoiceResponse = viewModel.invoiceResponse) {
        is Response.Loading -> ProgressBar()
        is Response.Success -> invoicesContent(invoiceResponse.data)
        is Response.Failure -> print(invoiceResponse.e)
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InvoiceScreen(viewModel: InvoiceViewModel = hiltViewModel()) {

    Invoices(viewModel = viewModel) { invoices ->
        val tabs = listOf("Facturas", "Albaranes", "Presupuestos")
        val pagerState = rememberPagerState(pageCount = { tabs.size })
        val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
        val scope = rememberCoroutineScope()

        Column(modifier = Modifier.fillMaxWidth()) {
            BarraBusqueda()

            Row(modifier = Modifier.padding(8.dp)) {
                tabs.forEachIndexed { index, title ->
                    Button(
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }, colors = ButtonDefaults.buttonColors(
                            containerColor = if (index == selectedTabIndex.value) {
                                BlueCobrado.copy(alpha = 0.63f)
                            } else {
                                Color.Transparent
                            },
                            contentColor = if (index == selectedTabIndex.value) {
                                Color.White
                            } else {
                                Color.Black
                            }
                        ),
                        contentPadding = PaddingValues(8.dp),
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier
                            .padding(4.dp)
                            .height(35.dp)
                    ) {
                        Text(text = title)
                    }
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
}

@Composable
fun InvoiceList(invoices: List<SelectInvoiceWithCustomer>) {
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
fun InvoiceCard(invoice: SelectInvoiceWithCustomer) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .wrapContentWidth()
            ) {
                Text(
                    text = "FACTURA${invoice.invoice_id}",
                    color = BlueInvoice,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = invoice.businessName, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "156,32 €", fontWeight = FontWeight.SemiBold)
            }
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center

            ) {
                RoundedBlueText(true)
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