package com.example.migestion.ui.navigation.graphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.migestion.ui.navigation.BottomNavItem
import com.example.migestion.ui.screens.createcustomerscreen.CreateCustomerScreen
import com.example.migestion.ui.screens.createinvoicescreen.CreateInvoiceScreen
import com.example.migestion.ui.screens.createproductscreen.CreateProduct
import com.example.migestion.ui.screens.customerscreen.CustomerScreen
import com.example.migestion.ui.screens.dashboardscreen.DashboardScreen
import com.example.migestion.ui.screens.invoicescreen.InvoiceScreen
import com.example.migestion.ui.screens.selectproductsscreen.SelectProduct

@Composable
fun HomeNavGraph(navController: NavHostController, padding: PaddingValues) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomNavItem.Dashboard.screen_route
    ) {
        composable(route = BottomNavItem.Dashboard.screen_route) {
            DashboardScreen()
        }
        composable(route = BottomNavItem.Customers.screen_route) {
            CustomerScreen(onItemClick = {})
        }
        composable(route = BottomNavItem.InvoiceScreen.screen_route) {
            InvoiceScreen(paddingValues = padding)
        }
        detailsNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.DETAILS, startDestination = DetailsScreen.FormularioAltaCliente.route
    ) {
        composable(route = DetailsScreen.FormularioAltaCliente.route) {
            CreateCustomerScreen(navController)
        }
        composable(route = DetailsScreen.Overview.route) {
            CreateInvoiceScreen(onSelectProduct = {
                navController.navigate(DetailsScreen.SelectProducts.route + "?invoiceId=$it")
            }, onBack = { navController.popBackStack() })
        }
        composable(
            route = DetailsScreen.SelectProducts.route + "?invoiceId={invoiceId}",
            arguments = listOf(navArgument("invoiceId") {
                type = NavType.StringType
            })
        ) {
            SelectProduct(
                onBack = { navController.popBackStack() }
            )
        }
        composable(route = DetailsScreen.CreateProduct.route) {
            CreateProduct(navController = navController)
        }
    }
}

sealed class DetailsScreen(val route: String) {
    object FormularioAltaCliente : DetailsScreen(route = "FORMULARIO")
    object Overview : DetailsScreen(route = "OVERVIEW")
    object CreateProduct : DetailsScreen(route = "create_product")

    object SelectProducts : DetailsScreen(route = "SELECCIONAR PRODUCTOS/{idInvoice}") {
        fun crearRoute(idInvoice: Int): String {
            return "SELECCIONAR PRODUCTOS/$idInvoice"
        }
    }
}