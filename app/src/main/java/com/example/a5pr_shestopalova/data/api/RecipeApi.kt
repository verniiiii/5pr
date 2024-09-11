package com.example.a5pr_shestopalova.data.api

import com.example.a5pr_shestopalova.data.model.Recipe
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeApi {
    @GET("recipes/id")
    suspend fun getRecipeById(@Path("id") id: Int): Recipe
}