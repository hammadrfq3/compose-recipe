package com.food.recipe.data.repo

import android.app.Application
import com.food.recipe.data.model.CategoryResponse
import com.food.recipe.data.model.MealRecipeResponse
import com.food.recipe.data.model.MealResponse
import com.food.recipe.domain.repository.RecipeRepository
import com.food.recipe.network.RecipeService


class RecipeRepositoryImpl(
    private val recipeService: RecipeService,
    appContext: Application
) : RecipeRepository {

    override suspend fun getCategories(): CategoryResponse {
        return recipeService.getCategories()
    }

    override suspend fun getMealByCategory(category: String): MealResponse {
        return recipeService.getMealByCategory(category)
    }

    override suspend fun getMealByMealId(category: String): MealRecipeResponse {
        return recipeService.getMealByMealId(category)
    }

}