package com.food.recipe.data.model

import androidx.annotation.Keep

@Keep
data class MealRecipeResponse(
    val meals: List<RecipeModel>
)