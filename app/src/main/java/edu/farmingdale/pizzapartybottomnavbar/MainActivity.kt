package edu.farmingdale.pizzapartybottomnavbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import edu.farmingdale.pizzapartybottomnavbar.ui.theme.*
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            // Start with the system's current theme
            val systemDarkTheme = isSystemInDarkTheme()

            // Use rememberSaveable to persist theme across orientation changes
            var isDarkTheme by rememberSaveable { mutableStateOf(systemDarkTheme) }

            // Apply your custom purple/blue theme (auto-switching)
            PizzaPartyBottomNavBarTheme(darkTheme = isDarkTheme) {

                // Update system bars to match theme
                SideEffect {
                    window.statusBarColor =
                        if (isDarkTheme) RoyalBlue.toArgb() else Orchid.toArgb()
                    window.navigationBarColor =
                        if (isDarkTheme) NavyBlue.toArgb() else Mauve.toArgb()

                    val controller =
                        WindowCompat.getInsetsController(window, window.decorView)
                    controller.isAppearanceLightStatusBars = !isDarkTheme
                    controller.isAppearanceLightNavigationBars = !isDarkTheme
                }

                // Render your app
                MainScreen(
                    isDarkTheme = isDarkTheme,
                    onThemeToggle = { isDarkTheme = !isDarkTheme }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    isDarkTheme: Boolean = false,
    onThemeToggle: () -> Unit = {}
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showBottomBar by remember { mutableStateOf(true) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showDrawer = currentRoute != BottomNavigationItems.Welcome.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            if (showDrawer) {
                ModalDrawerSheet(
                    drawerContainerColor =
                        if (isDarkTheme) MidnightBlue else Mauve
                ) {
                    DrawerContent(
                        currentRoute = currentRoute,
                        isDarkTheme = isDarkTheme,
                        onThemeToggle = onThemeToggle,
                        onItemClick = { route ->
                            scope.launch { drawerState.close() }
                            navController.navigate(route) {
                                popUpTo(BottomNavigationItems.PizzaScreen.route) {
                                    saveState = true
                                }
                                launchSingleTop = true
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
                                    BottomNavigationItems.Screen3.route -> "Screen 3"
                                    else -> "App"
                                }
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu")
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor =
                                if (isDarkTheme) RoyalBlue else Orchid,
                            titleContentColor = Color.White,
                            navigationIconContentColor = Color.White
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
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit,
    onItemClick: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(24.dp))

        DrawerItems.items.forEach { item ->
            val isSelected = currentRoute == item.route
            NavigationDrawerItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = isSelected,
                onClick = { onItemClick(item.route) },
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor =
                        if (isDarkTheme) SteelBlue else Orchid,
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    unselectedContainerColor = Color.Transparent,
                    unselectedIconColor =
                        if (isDarkTheme) BabyBlue else Indigo,
                    unselectedTextColor =
                        if (isDarkTheme) BabyBlue else Indigo
                )
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Divider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = if (isDarkTheme)
                SkyBlue.copy(alpha = 0.3f)
            else
                RebeccaPurple.copy(alpha = 0.3f)
        )

        // Theme toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector =
                        if (isDarkTheme) Icons.Default.DarkMode else Icons.Default.LightMode,
                    contentDescription = "Theme",
                    tint = if (isDarkTheme) BabyBlue else Indigo,
                    modifier = Modifier.padding(end = 12.dp)
                )
                Text(
                    text = if (isDarkTheme) "Dark Mode" else "Light Mode",
                    color = if (isDarkTheme) BabyBlue else Indigo,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Switch(
                checked = isDarkTheme,
                onCheckedChange = { onThemeToggle() },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor =
                        if (isDarkTheme) RoyalBlue else RebeccaPurple,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor =
                        if (isDarkTheme)
                            SkyBlue.copy(alpha = 0.5f)
                        else
                            Indigo.copy(alpha = 0.5f)
                )
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}
