package com.food.recipe.domain.repository

import com.food.recipe.data.model.CategoryResponse
import com.food.recipe.data.model.MealRecipeResponse
import com.food.recipe.data.model.MealResponse

interface RecipeRepository {

    /*suspend fun getCategories(): CategoryResponse {
        return recipeService.getCategories()
    }

    suspend fun getMealByCategory(category:String): MealResponse {
        return recipeService.getMealByCategory(category)
    }

    suspend fun getMealByMealId(category:String): MealRecipeResponse {
        return recipeService.getMealByMealId(category)
    }*/

    suspend fun getCategories() : CategoryResponse

    suspend fun getMealByCategory(category:String) : MealResponse

    suspend fun getMealByMealId(category:String) : MealRecipeResponse

}