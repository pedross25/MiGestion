package com.example.migestion.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.navArgument

sealed class AppScreens(
    val route: String,
    val arguments: List<NamedNavArgument>
) {

    object ProductScreen : AppScreens("productscreen", arguments = listOf(
        navArgument("productId") {
            nullable = true
        }
    ))

    object SignInScreen : AppScreens(route = "SignIn_Screen", emptyList())
    object SignUpScreen : AppScreens(route = "SignUp_Screen", emptyList())
    object HomeScreen : AppScreens(route = "home_screen", emptyList())
    object CustomerScreen : AppScreens(route = "customer_screen", emptyList())

    object CreateInvoiceScreen : AppScreens(route = "create_invoice_screen", emptyList())
    object BillScreen : AppScreens(route = "bill_screen", emptyList())
    object PartOfWorkScreen : AppScreens(route = "part_of_work_screen", emptyList())

}