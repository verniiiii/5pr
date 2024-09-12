package com.example.a5pr_shestopalova.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.a5pr_shestopalova.R
import com.example.a5pr_shestopalova.data.database.RecipeDao
import com.example.a5pr_shestopalova.data.model.Recipe
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeAdapter(
    private val recipes: MutableLiveData<List<Recipe>>,
    private val recipeDao: RecipeDao
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val ingredients: TextView = itemView.findViewById(R.id.ingredients)
        val instructions: TextView = itemView.findViewById(R.id.instructions)
        val calories: TextView = itemView.findViewById(R.id.calories)
        val image: ImageView = itemView.findViewById(R.id.image)
        val btnDelete: Button = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return RecipeViewHolder(view)
    }

    override fun getItemCount(): Int = recipes.value?.size ?: 0

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes.value?.get(position)
        if (recipe != null) {
            holder.name.text = recipe.name
            holder.ingredients.text = recipe.ingredients.toString()
            holder.instructions.text = recipe.instructions.toString()
            holder.calories.text = "Calories: " + recipe.caloriesPerServing.toString()
            Picasso.get().load(recipe.image).into(holder.image)

            holder.btnDelete.setOnClickListener {
                // Удалите элемент из базы данных и обновите адаптер
                CoroutineScope(Dispatchers.IO).launch {
                    recipeDao.delete(recipe)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Recipe>) {
        recipes.value = newList
        notifyDataSetChanged()
    }
}
