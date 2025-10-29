package edu.farmingdale.pizzapartybottomnavbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import edu.farmingdale.pizzapartybottomnavbar.ui.theme.PizzaPartyBottomNavBarTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PizzaPartyBottomNavBarTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showBottomBar by remember { mutableStateOf(true) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Don't show drawer on welcome screen
    val showDrawer = currentRoute != BottomNavigationItems.Welcome.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            if (showDrawer) {
                ModalDrawerSheet(
                    drawerContainerColor = Color(0xFFF5F5F5)
                ) {
                    DrawerContent(
                        currentRoute = currentRoute,
                        onItemClick = { route ->
                            scope.launch {
                                drawerState.close()
                            }
                            navController.navigate(route) {
                                // Pop up to the start destination to avoid building up a large stack
                                popUpTo(BottomNavigationItems.PizzaScreen.route) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        },
        gesturesEnabled = showDrawer
    ) {
        Scaffold(
            topBar = {
                if (showDrawer) {
                    TopAppBar(
                        title = {
                            Text(
                                text = when (currentRoute) {
                                    BottomNavigationItems.PizzaScreen.route -> "Pizza Order"
                                    BottomNavigationItems.GpaAppScreen.route -> "GPA App"
                                    BottomNavigationItems.Screen3.route -> "Screen3"
                                    else -> "App"
                                }
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Menu"
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                }
            },
            bottomBar = {
                if (showBottomBar && showDrawer) {
                    BottomBar(navController = navController, state = showBottomBar)
                }
            }
        ) { innerPadding ->
            NavigationGraph(
                navController = navController,
                onBottomBarVisibilityChanged = { showBottomBar = it },
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun DrawerContent(
    currentRoute: String?,
    onItemClick: (String) -> Unit
) {
    Spacer(modifier = Modifier.height(24.dp))

    DrawerItems.items.forEach { item ->
        val isSelected = currentRoute == item.route

        NavigationDrawerItem(
            icon = {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title
                )
            },
            label = {
                Text(text = item.title)
            },
            selected = isSelected,
            onClick = { onItemClick(item.route) },
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            colors = NavigationDrawerItemDefaults.colors(
                selectedContainerColor = Color(0xFFE1BEE7),
                selectedIconColor = Color(0xFF6200EE),
                selectedTextColor = Color.Black,
                unselectedContainerColor = Color.Transparent
            )
        )
    }
}