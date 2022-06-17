package com.example.recipegenie.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe(@PrimaryKey(autoGenerate = true) var recipeId: Int?,
                  @ColumnInfo(name = "isFavorite") var isFavorite: Boolean,
                  @ColumnInfo(name = "title") var title: String,
                  @ColumnInfo(name = "yields") var yields: String,
                  @ColumnInfo(name = "prepTime") var prepTime: String,
                  @ColumnInfo(name = "cookTime") var cookTime: String,
                  @ColumnInfo(name = "totalTime") var totalTime: String,
                  @ColumnInfo(name = "ingredients") var ingredients: String,
                  @ColumnInfo(name = "directions") var directions: String,
                  @ColumnInfo(name = "imageUrl") var imageUrl: String
                  )