package com.avirajsharma.recipeapp.presentation.composables

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController){
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search
    )
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = null)  },
                label = { Text(item.label) },
                selected = currentDestination == item.route,
                onClick = {
                    navController.navigate(item.route){
                        popUpTo(navController.graph.startDestinationId){ saveState = true }
                        launchSingleTop = true // prevents duplicate destinations in back stack.
                        restoreState = true //pehle se agar screen load ho chuki thi, to uska state restore karega.
                    }
                }
            )
        }
    }
}