package com.food.recipe.data.model

import androidx.annotation.Keep

@Keep
data class CategoryResponse(
    var categories: List<Category>
)