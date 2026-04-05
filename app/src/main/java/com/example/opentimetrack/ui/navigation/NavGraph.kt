package com.example.opentimetrack.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.opentimetrack.ui.home.HomeDestination
import com.example.opentimetrack.ui.home.HomeScreen
import com.example.opentimetrack.ui.time.TimeInstanceDestination
import com.example.opentimetrack.ui.time.TimeInstanceEntryDestination
import com.example.opentimetrack.ui.time.TimeInstanceEntryScreen
import com.example.opentimetrack.ui.time.TimeInstanceScreen
import com.example.opentimetrack.ui.time.TimeInstanceUpdateDestination
import com.example.opentimetrack.ui.time.TimeInstanceUpdateScreen
import com.example.opentimetrack.ui.type.TypeEntryDestination
import com.example.opentimetrack.ui.type.TypeEntryScreen
import com.example.opentimetrack.ui.type.TypeUpdateDestination
import com.example.opentimetrack.ui.type.TypeUpdateScreen

@Composable
fun TimeNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToTypeEntry = { navController.navigate(TypeEntryDestination.route) },
                navigateToTypeUpdate = { navController.navigate("${TypeUpdateDestination.route}/${it}") },
                navigateToTimeInstance = { navController.navigate("${TimeInstanceDestination.route}/${it}") }
            )
        }
        composable(
            route = TimeInstanceDestination.routeArg,
            arguments = listOf(navArgument(TimeInstanceDestination.typeIdArg) {
                type = NavType.IntType
            })
        ) {
            TimeInstanceScreen(
                navigateBack = { navController.popBackStack() },
                navigateToTimeInstanceEntry = { navController.navigate("${TimeInstanceEntryDestination.route}/${it}") },
                navigateToTimeInstanceUpdate = { navController.navigate("${TimeInstanceUpdateDestination.route}/${it}")},
            )
        }
    }
}