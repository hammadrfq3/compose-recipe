package com.food.recipe.ui.navigation

sealed class Screen(val route: String){
    object MainScreen : Screen(route = "main_screen")
    object MealScreen : Screen(route = "meal_screen")
    object MealDetailScreen : Screen(route = "meal_detail_screen")
    object SettingScreen : Screen(route = "setting_screen")
    object IntruderScreen : Screen(route = "intruder_screen")
    object LockScreen : Screen(route = "lock_screen")
    object VaultListScreen : Screen(route = "vault_list_screen")
    object AlbumScreen : Screen(route = "album_screen")
}
