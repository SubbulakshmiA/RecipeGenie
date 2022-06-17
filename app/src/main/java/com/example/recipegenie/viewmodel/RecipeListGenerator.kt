package com.example.recipegenie.viewmodel
/*
This class generates an ArrayList<Recipe> from RecipeResults
 */
import com.example.recipegenie.model.Recipe
import com.example.recipegenie.model.RecipeResults
import com.example.recipegenie.model.Results

class RecipeListGenerator {

    var recipeList = ArrayList<Recipe>()
    fun makeList(recipeResults: RecipeResults) : ArrayList<Recipe> {

            // create a recipe from search results
            if(!recipeResults.results.isNullOrEmpty()) {


                recipeResults.results = recipeResults.results.filter{it.sections != null} as ArrayList<Results>

                for (index in 0..recipeResults.results.lastIndex) {
                    // ingredients array to String
                    var ingredients = ""
                    for(i in 0..recipeResults.results[index].sections[0].components.lastIndex){
                        ingredients += recipeResults.results[index]
                            .sections[0].components[i].raw_text + "\n"}

                    // instructions[] to String
                    var directions = ""
                    for(i in 0..recipeResults.results[index].instructions.lastIndex){
                        directions += recipeResults.results[index]
                            .instructions[i].display_text + "\n"}

                    var id : Int = recipeResults.results[index].id
                    var isFavorite = false
                    var name : String =  recipeResults.results[index].name // AKA title
                    var yields: String = recipeResults.results[index].yields
                    var prepTime: String = "${recipeResults.results[index]
                        .prep_time_minutes} minutes"
                    var coockTime: String = "${recipeResults.results[index]
                        .cook_time_minutes} minutes"
                    var totalTime: String = "${recipeResults.results[index]
                        .total_time_minutes} minutes"
                    var imageUrl: String = recipeResults.results[index].thumbnail_url

                    var recipe = Recipe(null, false,"","","",
                        "", "", "", "","")

                    recipe.recipeId = id
                    recipe.isFavorite = isFavorite
                    recipe.title =name
                    recipe.yields = yields
                    recipe.prepTime = prepTime
                    recipe.cookTime = coockTime
                    recipe.totalTime = totalTime
                    recipe.ingredients = ingredients
                    recipe.directions = directions
                    recipe.imageUrl = imageUrl

                    recipeList.add(recipe)
                }

            }
        return recipeList
    }
}