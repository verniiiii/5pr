package com.example.a5pr_shestopalova.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a5pr_shestopalova.R
import com.example.a5pr_shestopalova.data.api.RecipeApi
import com.example.a5pr_shestopalova.data.database.RecipeDatabase
import com.example.a5pr_shestopalova.data.database.RecipeRepository
import com.example.a5pr_shestopalova.data.model.Recipe
import com.example.a5pr_shestopalova.databinding.ActivityMainBinding
import com.example.a5pr_shestopalova.ui.adapters.RecipeAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://dummyjson.com"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var repository: RecipeRepository
    private lateinit var adapter: RecipeAdapter
    private val recipesLiveData = MutableLiveData<List<Recipe>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val recipeApi = retrofit.create(RecipeApi::class.java)

        // Инициализация бд и репозитория
        val recipeDao = RecipeDatabase.getDatabase(applicationContext).recipeDao()
        repository = RecipeRepository(recipeDao)

        // Инициализация адаптера и RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RecipeAdapter(recipesLiveData)
        binding.recyclerView.adapter = adapter

        // Наблюдение за изменениями списка рецептов
        repository.allRecipes.observe(this) { newData ->
            adapter.updateList(newData)
        }

        binding.btnReceive.setOnClickListener {
            val id = binding.etId.text.toString().toIntOrNull()
            if (id != null && id in 1..50) {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val recipe = recipeApi.getRecipeById(id)
                        // Сохраните рецепт в базу данных
                        repository.insert(recipe)
                        // Обновите UI на основном потоке
                        runOnUiThread {
                            Toast.makeText(this@MainActivity, "Recipe saved", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        runOnUiThread {
                            Toast.makeText(this@MainActivity, "Error fetching recipe", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Please enter a number from 1 to 50", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

