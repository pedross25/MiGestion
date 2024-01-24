package com.example.migestion.ui.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.migestion.ui.navigation.BottomNavItem
import com.example.migestion.ui.screens.createcustomerscreen.CreateCustomerScreen
import com.example.migestion.ui.screens.createinvoicescreen.CreateInvoiceScreen
import com.example.migestion.ui.screens.customerscreen.CustomerScreen
import com.example.migestion.ui.screens.dashboardscreen.DashboardScreen
import com.example.migestion.ui.screens.invoicescreen.InvoiceScreen
import com.example.migestion.ui.screens.selectproductsscreen.SelectProduct
import com.example.migestion.ui.screens.selectproductsscreen.SelectProducts

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomNavItem.Home.screen_route
    ) {
        composable(route = BottomNavItem.Home.screen_route) {
            DashboardScreen()
        }
        composable(route = BottomNavItem.Customers.screen_route) {
            CustomerScreen(onItemClick = {})
        }
        composable(route = BottomNavItem.InvoiceScreen.screen_route) {
            InvoiceScreen()
        }
        detailsNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.DETAILS,
        startDestination = DetailsScreen.FormularioAltaCliente.route
    ) {
        composable(route = DetailsScreen.FormularioAltaCliente.route) {
        /*ScreenContent(name = DetailsScreen.Information.route) {
                navController.navigate(DetailsScreen.Overview.route)
            }*/
            //ClientForm()
            CreateCustomerScreen(navController)
        }
        composable(route = DetailsScreen.Overview.route) {
            /*ScreenContent(name = DetailsScreen.Overview.route) {
                navController.popBackStack(
                    route = DetailsScreen.Information.route,
                    inclusive = false
                )
            }*/
            CreateInvoiceScreen(navController)
        }
        composable(route = DetailsScreen.SelectProducts.route) {
            SelectProduct()
        }
    }
}

sealed class DetailsScreen(val route: String) {
    object FormularioAltaCliente : DetailsScreen(route = "FORMULARIO")
    object Overview : DetailsScreen(route = "OVERVIEW")

    object SelectProducts: DetailsScreen(route = "SELECCIONAR PRODUCTOS")
}