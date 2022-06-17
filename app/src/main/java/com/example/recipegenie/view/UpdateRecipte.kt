package com.example.recipegenie.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.recipegenie.R
import com.example.recipegenie.model.AppDatabase
import com.example.recipegenie.model.Recipe
import com.example.recipegenie.model.RecipeRepository
import com.example.recipegenie.viewmodel.MainViewModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.textfield.TextInputEditText


class UpdateRecipe : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel

    private lateinit var id: TextView
    private lateinit var title: TextInputEditText
    private lateinit var yields: TextInputEditText
    private lateinit var prepTime: TextInputEditText
    private lateinit var totalTime: TextInputEditText
    private lateinit var ingredients: TextInputEditText
    private lateinit var directions: TextInputEditText

    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_recipe)

        var dao = AppDatabase.getInstance(this)?.recipeDao()!!
        var repo = RecipeRepository(dao)

        mainViewModel = MainViewModel(repo)
        var recipe = mainViewModel.findRecipeWithTitle(intent.getStringExtra("title")!!)[0]
        val btnUpdate: ExtendedFloatingActionButton = findViewById(R.id.btn_update)
        val btnCancel: ExtendedFloatingActionButton = findViewById(R.id.btn_cancel)

        populateFields(recipe)


        btnCancel.setOnClickListener {
            val message = "cancelled"
            val duration = Toast.LENGTH_LONG
            val toast = Toast.makeText(applicationContext, message, duration)
            toast.show()

            val intent = Intent(this, RecipeListActivity::class.java)
            startActivity(intent)}

        btnUpdate.setOnClickListener {
            var recipeUpd = Recipe(
                recipe.recipeId, recipe.isFavorite, title.text.toString(),
                yields.text.toString(), prepTime.text.toString(),recipe.cookTime, totalTime.text.toString(),
                ingredients.text.toString(), directions.text.toString(), recipe.imageUrl
            )
            mainViewModel.updateRecipe(recipeUpd)

            val message = "recipe updated"
            val duration = Toast.LENGTH_LONG
            val toast = Toast.makeText(applicationContext, message, duration)
            toast.show()

            val intent = Intent(this, RecipeListActivity::class.java)
            startActivity(intent)
        }
    }

    private fun populateFields(recipe: Recipe) {
//         Map TextViews in recipe page
        id = findViewById(R.id.text_view_id)
        title = findViewById(R.id.edit_text_title)
        yields = findViewById(R.id.edit_text_yield)
        prepTime = findViewById(R.id.edit_text_prep_time)
        totalTime = findViewById(R.id.edit_text_cook_time)
        ingredients = findViewById(R.id.edit_text_ingredients)
        directions = findViewById(R.id.edit_text_directions)

        // Populate Text Views with recipe fields
        id.text = recipe.recipeId.toString()
        title.setText(recipe.title)
        yields.setText(recipe.yields)
        prepTime.setText(recipe.prepTime)
        totalTime.setText(recipe.totalTime)
        ingredients.setText(recipe.ingredients)
        directions.setText(recipe.directions)
    }
}