package com.food.recipe.data.model

import androidx.annotation.Keep

@Keep
data class MealResponse(
    val meals: List<Meal>
)