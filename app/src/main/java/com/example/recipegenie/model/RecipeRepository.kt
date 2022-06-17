package com.example.recipegenie.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeRepository (var dao: RecipeDao) {

   // var db: RecipeDao? = AppDatabase.getInstance(context)?.recipeDao()
    var retrofitClient = RetrofitClient.create()
    var searchResults = MutableLiveData<RecipeResults>()

    // Gets recipes from API and returns MutableLiveData<RecipeResults>
    fun getSearchResults(offset: Int, limit: Int, tags: String, search: String) :
    MutableLiveData<RecipeResults>{
        CoroutineScope(Dispatchers.IO).launch {

            var res = retrofitClient.getSearchResults(offset, limit, tags, search)

            if(res.isSuccessful) {
                searchResults.postValue(res.body())
                Log.d("Retrofit Response", "Successful")

            } else {
                Log.d("Retrofit Response", "unsuccessful: RecipeRepository: Line 30")
            }
        }
        return searchResults
    }


    // Gets recipes from Room DB and returns LiveData<List<Recipe>>
    fun getAllRecipes(): LiveData<List<Recipe>>? {

        return dao.selectRecipe()
    }

    fun insertRecipe(recipe: Recipe) {
        dao.insertRecipe(recipe)
    }

    fun updateRecipe(recipe: Recipe) {
        dao.updateRecipe(recipe)
    }

    fun deleteRecipe(recipe: Recipe) {
        dao.deleteRecipe(recipe)
    }

    fun deleteAll() {
        dao.deleteAll()
    }
//
//    fun findRecipeWithId(search: String): List<Recipe> {
//
//        return db?.findRecipeWithId(search)!!
//    }
//
    fun findRecipeWithTitle(search: String): List<Recipe> {

        return dao.findRecipeWithTitle(search)!!
    }

    fun search(searchText:String): LiveData<List<Recipe>>? {
        return dao.search(searchText)
    }

    // insert things in an Async way
}