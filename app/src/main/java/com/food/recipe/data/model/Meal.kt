package com.food.recipe.data.model

import androidx.annotation.Keep

@Keep
data class Meal(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)