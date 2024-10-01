package com.food.recipe.di

import android.app.Application
import com.food.recipe.domain.repository.RecipeRepository
import com.food.recipe.data.repo.RecipeRepositoryImpl
import com.food.recipe.network.RecipeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRecipeService(): RecipeService {
        return Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RecipeService::class.java)
    }

    @Provides
    @Singleton
    fun provideRecipeRepository2(recipeService: RecipeService,app: Application): RecipeRepository {
        return RecipeRepositoryImpl(recipeService,app)
    }


}