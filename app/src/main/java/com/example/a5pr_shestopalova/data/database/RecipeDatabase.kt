package com.example.a5pr_shestopalova.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.a5pr_shestopalova.data.model.Recipe

@Database(entities = [Recipe::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao():RecipeDao

    companion object{
        //Аннотация, которая гарантирует,
        // что изменения переменной будут видны всем потокам.
        // Это важно для многопоточного доступа.
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        fun getDatabase(context: Context): RecipeDatabase{
            //synchronized(this): Блок кода, который выполняется синхронно,
            // чтобы избежать одновременного доступа нескольких потоков.
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "recipe_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}