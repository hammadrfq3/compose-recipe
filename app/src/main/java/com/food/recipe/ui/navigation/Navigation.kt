package com.food.recipe.ui.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.food.recipe.R
import com.food.recipe.ui.screens.MainScreen
import com.food.recipe.ui.screens.MealDetailScreen
import com.food.recipe.ui.screens.MealScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun Navigation() {

    val colors = MaterialTheme.colorScheme
    //val systemUiController = rememberSystemUiController()
    var statusBarColor by remember { mutableStateOf(colors.primary) }
    var navigationBarColor by remember { mutableStateOf(colors.primary) }

    val animatedStatusBarColor by animateColorAsState(
        targetValue = statusBarColor,
        animationSpec = tween()
    )
    val animatedNavigationBarColor by animateColorAsState(
        targetValue = navigationBarColor,
        animationSpec = tween()
    )

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(Screen.MainScreen.route) {
            MainScreen {
                navController.navigate("${Screen.MealScreen.route}/$it")
            }
        }
        /*composable(Screen.SettingScreen.route){
            SettingScreen(navController)
        }
        composable(Screen.IntruderScreen.route){
            IntruderScreen(navController)
        }
        composable(Screen.LockScreen.route){
            LockView(navController)
        }
        composable(Screen.AlbumScreen.route){
            AlbumScreen(navController)
        }*/
        composable(
            Screen.MealScreen.route.plus("/{category}"),
            arguments = listOf(navArgument("category") { type = NavType.StringType }
            )) { b ->
            b.arguments?.getString("category").let { category ->
                if (category != null) {
                    MealScreen(category = category, {
                        navController.navigate("${Screen.MealDetailScreen.route}/${it.idMeal}")
                    }) {
                        //navController.navigate("${Screen.MealScreen.route}/$it")
                        navController.navigateUp()
                    }
                }
            }
        }
        composable(
            Screen.MealDetailScreen.route.plus("/{meal}"),
            arguments = listOf(
                navArgument("meal") { type = NavType.Companion.StringType })
        )
        { b ->
            b.arguments?.getString("meal").let { meal ->
                if (meal != null) {
                    MealDetailScreen(meal = meal) {
                        navController.navigateUp()
                    }
                }
            }
        }
    }

    /*LaunchedEffect(animatedStatusBarColor, animatedNavigationBarColor) {
        systemUiController.setStatusBarColor(color = colorResource(id = R.color.bg))
        systemUiController.setNavigationBarColor(color = colorResource(id = R.color.bg))
    }*/

}