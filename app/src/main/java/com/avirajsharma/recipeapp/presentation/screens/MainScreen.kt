package com.avirajsharma.recipeapp.presentation.screens

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.WindowMetricsCalculator
import com.avirajsharma.recipeapp.presentation.composables.BottomNavItem
import com.avirajsharma.recipeapp.presentation.composables.BottomNavigationBar
import com.avirajsharma.recipeapp.presentation.navigation.RecipeNavigation

@SuppressLint("ContextCastToActivity")
@Composable
fun RecipeApp() {
    val navController = rememberNavController()
    val windowSizeClass = calculateWindowSizeClass(LocalContext.current as Activity)

    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            CompactLayout(navController = navController)
        }

        WindowWidthSizeClass.Medium -> {
            MediumLayout(navController = navController)
        }

        WindowWidthSizeClass.Expanded -> {
            ExpandedLayout(navController = navController)
        }
    }
}

@Composable
private fun CompactLayout(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        RecipeNavigation(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun MediumLayout(navController: NavHostController) {
    Row(modifier = Modifier.fillMaxSize()) {
        NavigationRail(
            modifier = Modifier.safeDrawingPadding()
        ) {
            val items = listOf(
                BottomNavItem.Home,
                BottomNavItem.Search
            )
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            Spacer(modifier = Modifier.weight(1f))

            items.forEach { item ->
                val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true

                NavigationRailItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.contentDescription
                        )
                    },
                    label = {
                        Text(
                            text = item.label,
                            style = MaterialTheme.typography.labelSmall
                        )
                    },
                    selected = selected,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.weight(1f))
        }

        RecipeNavigation(
            navController = navController,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun ExpandedLayout(navController: NavHostController) {
    PermanentNavigationDrawer(
        drawerContent = {
            PermanentDrawerSheet(modifier = Modifier.width(280.dp)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            modifier = Modifier.size(48.dp),
                            shape = CircleShape,
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "🍳",
                                    fontSize = 24.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Text(
                            text = "Recipe App",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                    val items = listOf(
                        BottomNavItem.Home,
                        BottomNavItem.Search
                    )
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    items.forEach { item ->
                        val selected =
                            currentDestination?.hierarchy?.any { it.route == item.route } == true

                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = null
                                )
                            },
                            label = {
                                Text(
                                    text = item.label,
                                    style = MaterialTheme.typography.labelLarge
                                )
                            },
                            selected = selected,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                    }
                }
            }
        }
    ) {
        RecipeNavigation(
            navController = navController,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun calculateWindowSizeClass(activity: Activity): WindowSizeClass {
    val metrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(activity)
    val width = metrics.bounds.width()
    val height = metrics.bounds.height()
    val density = LocalDensity.current.density

    val widthDp = (width / density).dp
    val heightDp = (height / density).dp

    when {
        widthDp < 600.dp -> WindowWidthSizeClass.Compact
        widthDp < 840.dp -> WindowWidthSizeClass.Medium
        else -> WindowWidthSizeClass.Expanded
    }

    when {
        heightDp < 480.dp -> WindowHeightSizeClass.Compact
        heightDp < 900.dp -> WindowHeightSizeClass.Medium
        else -> WindowHeightSizeClass.Expanded
    }

    return WindowSizeClass.calculateFromSize(
        DpSize(widthDp, heightDp)
    )
}

enum class WindowHeightSizeClass {
    Compact,
    Medium,
    Expanded
}

data class WindowSizeClass(
    val widthSizeClass: WindowWidthSizeClass,
    val heightSizeClass: WindowHeightSizeClass
) {
    companion object {
        fun calculateFromSize(size: DpSize): WindowSizeClass {
            val widthSizeClass = when {
                size.width < 600.dp -> WindowWidthSizeClass.Compact
                size.width < 840.dp -> WindowWidthSizeClass.Medium
                else -> WindowWidthSizeClass.Expanded
            }

            val heightSizeClass = when {
                size.height < 480.dp -> WindowHeightSizeClass.Compact
                size.height < 900.dp -> WindowHeightSizeClass.Medium
                else -> WindowHeightSizeClass.Expanded
            }

            return WindowSizeClass(widthSizeClass, heightSizeClass)
        }
    }
}