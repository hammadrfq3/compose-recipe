package com.food.recipe.data.model

import com.google.gson.Gson

class MealArgType : JsonNavType<Meal>() {
    override fun fromJsonParse(value: String): Meal =
        Gson().fromJson(value, Meal::class.java)

    override fun Meal.getJsonParse(): String = Gson().toJson(this)
}