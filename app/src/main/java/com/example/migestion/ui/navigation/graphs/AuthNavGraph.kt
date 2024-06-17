package com.example.migestion.ui.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.migestion.ui.screens.signinscreen.SignInScreen
import com.example.migestion.ui.screens.signupscreen.SignUpScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.SignIn.route
    ) {
        composable(route = AuthScreen.SignIn.route) {
            SignInScreen(
                onSignInClick = {
                    navController.popBackStack()
                    navController.navigate(Graph.HOME)
                },
                onSignUpClick = {
                    navController.navigate(AuthScreen.SignUp.route)
                }
            )
        }
        composable(route = AuthScreen.SignUp.route) {
            SignUpScreen(onSignInClick = {
                navController.navigate(AuthScreen.SignIn.route)
            })
        }
    }
}

sealed class AuthScreen(val route: String) {
    object SignIn : AuthScreen(route = "SIGN_IN")
    object SignUp : AuthScreen(route = "SIGN_UP")
}