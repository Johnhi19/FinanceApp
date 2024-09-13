package com.example.finanzapp.navigation

import com.example.finanzapp.objects.OutgoingsEntry

sealed class Screen(val route: String){
    object MainScreen : Screen("main_screen")
    object AddScreen : Screen("add_screen")

    fun withArgs(vararg args: List<OutgoingsEntry>): String {
        return buildString {
            append(route)
            args.forEach { arg->
                append("/$arg")
            }
        }
    }
}
