package com.example.recipegenie.viewmodel


import android.app.Application
import androidx.lifecycle.*
import com.example.recipegenie.model.RecipeRepository
import com.example.recipegenie.model.Recipe
import com.example.recipegenie.model.RecipeResults
import kotlinx.coroutines.launch

class MainViewModel(  private val repo: RecipeRepository): ViewModel() {

    val recipeList : LiveData<List<Recipe>>?
    lateinit var searchResults : MutableLiveData<RecipeResults>
    private val _search =MutableLiveData<String>()

    init {
        recipeList = repo.getAllRecipes()
        searchResults = MutableLiveData()
        _search.value = ""
    }

    fun getSearchResults(offset: Int, limit: Int, tags: String, search: String)
    = viewModelScope.launch{

        searchResults = repo.getSearchResults(offset, limit, tags, search)
    }

    fun getAllRecipes() = viewModelScope.launch {
        repo.getAllRecipes()
    }

    fun insertRecipes(recipe: Recipe) = viewModelScope.launch {
        repo.insertRecipe(recipe)
    }

    fun updateRecipe(recipe: Recipe) = viewModelScope.launch {
        repo.updateRecipe(recipe)
    }

    fun deleteRecipe(recipe: Recipe) = viewModelScope.launch {
        repo.deleteRecipe(recipe)
    }

    fun findRecipeWithTitle(search: String): List<Recipe> {

        return repo.findRecipeWithTitle(search)
    }

    var results = Transformations.switchMap(_search ){ string->
        if(string != ""){
            repo.search(string)
        }else{
            repo.getAllRecipes()
        }
    }

    fun searchIn(text: String) =viewModelScope.launch{
        _search.value = text
    }
}