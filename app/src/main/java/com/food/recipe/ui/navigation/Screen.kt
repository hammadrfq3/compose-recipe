package com.food.recipe.ui.navigation

sealed class Screen(val route: String){
    object MainScreen : Screen(route = "main_screen")
    object MealScreen : Screen(route = "meal_screen")
    object MealDetailScreen : Screen(route = "meal_detail_screen")
}
