package com.food.recipe.data.model

import android.net.Uri
import androidx.annotation.Keep
import com.google.gson.Gson

@Keep
data class Category(
    val idCategory: String,
    val strCategory: String,
    val strCategoryDescription: String,
    val strCategoryThumb: String
)  {
    override fun toString(): String = Uri.encode(Gson().toJson(this))
}