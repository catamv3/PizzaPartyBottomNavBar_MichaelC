package edu.farmingdale.pizzapartybottomnavbar

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import edu.farmingdale.pizzapartybottomnavbar.ui.theme.BabyBlue
import edu.farmingdale.pizzapartybottomnavbar.ui.theme.Indigo
import edu.farmingdale.pizzapartybottomnavbar.ui.theme.Mauve
import edu.farmingdale.pizzapartybottomnavbar.ui.theme.Orchid
import edu.farmingdale.pizzapartybottomnavbar.ui.theme.RebeccaPurple
import edu.farmingdale.pizzapartybottomnavbar.ui.theme.RoyalBlue
import edu.farmingdale.pizzapartybottomnavbar.ui.theme.SkyBlue


@Composable
fun BottomBar(
    navController: NavHostController,
    state: Boolean,
    modifier: Modifier = Modifier
) {
    val screens = listOf(
        BottomNavigationItems.PizzaScreen,
        BottomNavigationItems.GpaAppScreen,
        BottomNavigationItems.Screen3
    )

    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primaryContainer,  // Colorful purple/blue container
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach { screen ->
            val isSelected = currentRoute == screen.route

            NavigationBarItem(
                label = {
                    Text(
                        text = screen.title!!,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) {
                            MaterialTheme.colorScheme.onSecondary  // White on selected
                        } else {
                            MaterialTheme.colorScheme.onPrimaryContainer  // Darker on unselected
                        }
                    )
                },
                icon = {
                    Icon(
                        imageVector = screen.icon!!,
                        contentDescription = screen.title,
                        tint = if (isSelected) {
                            MaterialTheme.colorScheme.onSecondary  // White on selected
                        } else {
                            MaterialTheme.colorScheme.onPrimaryContainer  // Darker on unselected
                        }
                    )
                },
                selected = isSelected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onSecondary,
                    selectedIconColor = MaterialTheme.colorScheme.onSecondary,
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    indicatorColor = MaterialTheme.colorScheme.secondary  // Vibrant secondary color
                ),
            )
        }
    }
}