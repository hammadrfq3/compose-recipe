package com.food.recipe.network

import com.food.recipe.data.model.CategoryResponse
import com.food.recipe.data.model.MealRecipeResponse
import com.food.recipe.data.model.MealResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeService {

  @GET("categories.php")
  suspend fun getCategories(): CategoryResponse

  @GET("filter.php")
  suspend fun getMealByCategory(@Query("c") category:String): MealResponse

  @GET("lookup.php")
  suspend fun getMealByMealId(@Query("i") category:String): MealRecipeResponse
}