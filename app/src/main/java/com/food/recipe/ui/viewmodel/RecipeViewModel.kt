package com.food.recipe.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.food.recipe.data.model.Category
import com.food.recipe.data.model.CategoryResponse
import com.food.recipe.data.model.MealRecipeResponse
import com.food.recipe.data.model.MealResponse
import com.food.recipe.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repository: RecipeRepository
): ViewModel() {
    //private val repository = RecipeRepository()

    private var _searchText = mutableStateOf("")
    var searchText : MutableState<String> = _searchText

    fun getCategoriesByFilter(searchText:String){
        viewModelScope.launch{
            if (searchText.isNotEmpty()){
                Log.e("TAG","search : $searchText")
                val list = categories.value?.categories?.filter { c -> c.strCategory.contains(searchText,true) }
                _categoriesLocal = list as MutableList<Category>
                categoriesLocal.emit(_categoriesLocal)
            }else {
                _categoriesLocal = categories.value?.categories as MutableList<Category>
                categoriesLocal.emit(_categoriesLocal)
            }
        }
    }

    val categoriesLocal = MutableSharedFlow<List<Category>>()
    private var _categoriesLocal = mutableListOf<Category>()

    private val _categories = MutableLiveData<CategoryResponse>()
    val categories: LiveData<CategoryResponse> = _categories

    private val _meals = MutableLiveData<MealResponse>()
    val meals: LiveData<MealResponse> = _meals

    private val _mealItem = MutableLiveData<MealRecipeResponse>()
    val mealItem: LiveData<MealRecipeResponse> = _mealItem

    private var categoriesFetched = false // Flag to track if categories have been fetched

    fun fetchCategoriesIfNeeded() {
        if (!categoriesFetched) {
            fetchCategories()
        }
    }

     private fun fetchCategories() {
        viewModelScope.launch {
            try {
                val cat = repository.getCategories()
                _categories.value = cat
                _categoriesLocal.addAll(cat.categories)
                categoriesLocal.emit(_categoriesLocal)
                categoriesFetched = true // Set flag to true after fetching categories
            } catch (e: Exception) {
                Log.e("TAG", e.message.toString())
                // Handle error
            }
        }
    }

    fun fetchMealsByCategory(category:String) {
        viewModelScope.launch {
            try {
                val meal = repository.getMealByCategory(category)
                _meals.value = meal
            } catch (e: Exception) {
                Log.e("TAG", e.message.toString())
                // Handle error
            }
        }
    }

    fun fetchMealByMealId(mealId:String) {
        viewModelScope.launch {
            try {
                val meal = repository.getMealByMealId(mealId)
                _mealItem.value = meal
            } catch (e: Exception) {
                Log.e("TAG", e.message.toString())
                // Handle error
            }
        }
    }
}