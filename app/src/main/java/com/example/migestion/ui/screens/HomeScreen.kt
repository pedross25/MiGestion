package com.example.migestion.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.migestion.R
import com.example.migestion.ui.navigation.BottomNavItem
import com.example.migestion.ui.navigation.graphs.DetailsScreen
import com.example.migestion.ui.navigation.graphs.HomeNavGraph

@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {
    BottomBarWithFabDem(navController)
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomBarWithFabDem(navController: NavHostController) {
    var expanded by remember { mutableStateOf(false) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val screens = listOf(
        BottomNavItem.Dashboard,
        BottomNavItem.Customers,
        BottomNavItem.InvoiceScreen,
        BottomNavItem.Notification
    )
    val bottomBarDestination = screens.any { it.screen_route == currentDestination?.route }

    Scaffold(
        bottomBar = {
            if (bottomBarDestination) {
                BottomAppBar(
                    modifier = Modifier.height(65.dp)
                ) {
                    BottomBar(navController = navController)
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            if (bottomBarDestination) {
                Box() {
                    FloatingActionButton(
                        shape = CircleShape,
                        onClick = {
                            //handleFabClick(navController, currentDestination)
                            expanded = true

                        },
                        contentColor = Color.Black,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(65.dp)
                            .offset(y = 55.dp)
                            .border(2.dp, color = Color.White, CircleShape)
                    ) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add icon")
                    }
                }
            }

        },
    ) { innerPadding ->
        HomeNavGraph(navController = navController, innerPadding)
        val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
        if (expanded) {
            ModalBottomSheet(onDismissRequest = { expanded = false }) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Facturación", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                    Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(8.dp))
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable {
                                expanded = false
                                navController.navigate(DetailsScreen.Overview.route)
                            }) {
                        Icon(
                            painter = painterResource(id = R.drawable.document_add),
                            contentDescription = "My Icon",
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Crear factura",
                        )
                    }
                    Spacer(modifier = Modifier.padding(20.dp))
                    Text("Productos", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                    Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(8.dp))
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable {
                                expanded = false
                                navController.navigate(DetailsScreen.CreateProduct.route)
                            }) {
                        Icon(
                            painter = painterResource(id = R.drawable.document_add),
                            contentDescription = "My Icon",
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Crear producto",
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavController) {
    val screensLeft = listOf(
        BottomNavItem.Dashboard,
        BottomNavItem.Customers,
    )

    val screensRight = listOf(
        BottomNavItem.InvoiceScreen,
        BottomNavItem.Notification,
    )

    val navStackBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navStackBackEntry?.destination

    Row(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 8.dp)
            .background(Color.Transparent)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Botones izquierdos
        Row(
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            screensLeft.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }

        // Botones derechos
        Row(
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            screensRight.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomNavItem, currentDestination: NavDestination?, navController: NavController
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.screen_route } == true

    val contentColor = if (selected) Color.Black else Color(0xFFB3B3B3)

    IconButton(onClick = {
        navController.navigate(screen.screen_route) {}
    }) {
        Icon(
            painter = painterResource(id = if (selected) screen.icon else screen.icon),
            contentDescription = "icon",
            tint = contentColor
        )
    }
}


fun handleFabClick(navController: NavController, currentDestination: NavDestination?) {
    when (currentDestination?.route) {
        // Agrega los casos para cada pantalla donde quieres realizar una acción específica
        BottomNavItem.Customers.screen_route -> {
            navController.navigate(DetailsScreen.FormularioAltaCliente.route)
        }

        BottomNavItem.InvoiceScreen.screen_route -> {
            navController.navigate(DetailsScreen.Overview.route)
        }

        BottomNavItem.Dashboard.screen_route -> {
            navController.navigate(DetailsScreen.Overview.route)
        }
        // Agrega más casos según sea necesario
        else -> {
            // Acción predeterminada o manejo genérico
        }
    }
}