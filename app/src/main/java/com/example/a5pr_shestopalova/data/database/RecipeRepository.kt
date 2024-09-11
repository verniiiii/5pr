package com.example.a5pr_shestopalova.data.database

import androidx.lifecycle.LiveData
import com.example.a5pr_shestopalova.data.model.Recipe

class RecipeRepository(private val recipeDao: RecipeDao) {
    val allRecipes: LiveData<List<Recipe>> = recipeDao.getAllRecipes()

    suspend fun insert(recipe: Recipe) {
        recipeDao.insert(recipe)
    }

    suspend fun delete(recipe: Recipe) {
        recipeDao.delete(recipe)
    }
}
