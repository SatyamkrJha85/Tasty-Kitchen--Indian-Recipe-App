package com.recipe.recipeapphome

import androidx.room.Dao
import androidx.room.Query
@Dao
abstract class Dao {
    @Query("SELECT * FROM recipe")
    abstract fun getAll():List<Recipe>
}