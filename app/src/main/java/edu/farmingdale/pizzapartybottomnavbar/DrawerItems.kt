package edu.farmingdale.pizzapartybottomnavbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.ui.graphics.vector.ImageVector

// Data class for drawer navigation items
// Colors for drawer items are applied in MainActivity's DrawerContent composable
// using the purple theme palette (Orchid, Indigo, Mauve, etc.)
data class DrawerItem(
    val route: String,
    val title: String,
    val icon: ImageVector
)

object DrawerItems {
    val items = listOf(
        DrawerItem(
            route = BottomNavigationItems.PizzaScreen.route,
            title = "Pizza Order",
            icon = Icons.Default.Restaurant  // Pizza/Restaurant icon for Pizza Order
        ),
        DrawerItem(
            route = BottomNavigationItems.GpaAppScreen.route,
            title = "GPA App",
            icon = Icons.Default.Calculate  // Calculator icon for GPA App
        ),
        DrawerItem(
            route = BottomNavigationItems.Screen3.route,
            title = "Screen 3",  // Updated to match MainActivity
            icon = Icons.Default.Person  // Person icon for Profile
        )
    )
}