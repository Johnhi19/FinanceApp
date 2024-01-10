package com.example.finanzapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(
            route = Screen.MainScreen.route + "/{outgoing}/{description}",
            arguments = listOf(
                navArgument("outgoing"){
                    type = NavType.StringType
                    nullable = false
                },
                navArgument("description"){
                    type = NavType.StringType
                    nullable = false
                }
                )
        ){entry ->
            MainScreen(
                navController = navController,
                outgoing = entry.arguments?.getString("outgoing")!!,
                description = entry.arguments?.getString("description")!!)
        }
        composable(route = Screen.AddScreen.route) {
            AddScreen(navController = navController)
        }
    }
}