package com.example.a5pr_shestopalova.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey val id: Int = 0,
    val name: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    val caloriesPerServing: Int,
    val image: String
)