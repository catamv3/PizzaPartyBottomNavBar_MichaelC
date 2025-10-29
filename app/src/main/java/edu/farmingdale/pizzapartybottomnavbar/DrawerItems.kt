package edu.farmingdale.pizzapartybottomnavbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector

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
            icon = Icons.Default.ShoppingCart
        ),
        DrawerItem(
            route = BottomNavigationItems.GpaAppScreen.route,
            title = "GPA App",
            icon = Icons.Default.Info
        ),
        DrawerItem(
            route = BottomNavigationItems.Screen3.route,
            title = "Screen3",
            icon = Icons.Default.Person
        )
    )
}