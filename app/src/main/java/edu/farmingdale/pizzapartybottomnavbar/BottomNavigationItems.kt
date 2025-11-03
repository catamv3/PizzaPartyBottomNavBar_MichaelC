package edu.farmingdale.pizzapartybottomnavbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview


// Sealed class for bottom navigation items with route, title, and icon
sealed class BottomNavigationItems(
    val route: String,
    val title: String?,
    val icon: ImageVector?
) {
    // Welcome/Splash screen - no bottom nav here
    object Welcome : BottomNavigationItems(
        route = "welcome",
        title = null,
        icon = null
    )

    // Pizza Order Screen with pizza icon
    object PizzaScreen : BottomNavigationItems(
        route = "pizza_screen",
        title = "Pizza Order",
        icon = Icons.Default.Restaurant
    )

    // GPA Calculator Screen with calculator icon
    object GpaAppScreen : BottomNavigationItems(
        route = "gpa_app_screen",
        title = "GPA App",
        icon = Icons.Default.Calculate
    )

    // Profile/Screen3 with person icon
    object Screen3 : BottomNavigationItems(
        route = "screen3",
        title = "Screen 3",
        icon = Icons.Default.Person
    )
}
