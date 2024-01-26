package com.food.recipe.data.repo

import com.food.recipe.data.model.CategoryResponse
import com.food.recipe.data.model.MealRecipeResponse
import com.food.recipe.data.model.MealResponse
import com.food.recipe.network.RetrofitInstance

class RecipeRepository {
    private val recipeService = RetrofitInstance.recipeService

    suspend fun getCategories(): CategoryResponse {
        return recipeService.getCategories()
    }

    suspend fun getMealByCategory(category:String): MealResponse {
        return recipeService.getMealByCategory(category)
    }

    suspend fun getMealByMealId(category:String): MealRecipeResponse {
        return recipeService.getMealByMealId(category)
    }
}