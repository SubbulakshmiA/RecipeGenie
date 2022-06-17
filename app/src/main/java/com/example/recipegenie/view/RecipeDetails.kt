package com.example.recipegenie.view

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import coil.load
import com.example.recipegenie.MainActivity
import com.example.recipegenie.R
import com.example.recipegenie.model.AppDatabase
import com.example.recipegenie.model.RecipeRepository
import com.example.recipegenie.model.Recipe
import com.example.recipegenie.viewmodel.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.snackbar.Snackbar

class RecipeDetails : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_page)

        var dao = AppDatabase.getInstance(this)?.recipeDao()!!
        var repo = RecipeRepository(dao)

        mainViewModel = MainViewModel(repo)

//        var recipe = mainViewModel.findRecipeWithTitle(intent.getStringExtra("title")!!)[0]
//        populateFields(recipe)

        var id = intent.getIntExtra("id", 0)
        var isFavorite = intent.getBooleanExtra("isFavorite", false)
        var title = intent.getStringExtra("title")!!
        var yields = intent.getStringExtra("yields")!!
        var prepTime = intent.getStringExtra("prepTime")!!
        var cookTime = intent.getStringExtra("cookTime")!!
        var totalTime = intent.getStringExtra("totalTime")!!
        var ingredients = intent.getStringExtra("ingredients")!!
        var directions = intent.getStringExtra("directions")!!
        var imageUrl = intent.getStringExtra("imageUrl")!!


        var recipe =  Recipe(id,isFavorite, title, yields, prepTime, cookTime, totalTime, ingredients, directions, imageUrl)

        populateFields(recipe)



        val btnHome: ExtendedFloatingActionButton = findViewById(R.id.btn_cancel)
        btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val btnEdit: ExtendedFloatingActionButton = findViewById(R.id.btn_edit)
        btnEdit.setOnClickListener {
            val intent: Intent = Intent(this, UpdateRecipe::class.java)
            intent.putExtra("title", recipe.title)
            startActivity(intent)
        }

        val btnDelete: ExtendedFloatingActionButton = findViewById(R.id.btn_delete)
        btnDelete.setOnClickListener {

                MaterialAlertDialogBuilder(this)
                    .setTitle("Alert")
                    .setMessage("This will permanently delete '$title'. Do you want to continue?")
                    .setPositiveButton("Cancel", null)
                    .setNegativeButton("Delete") {dialog, which ->
                        mainViewModel.deleteRecipe(recipe)
                        val intent: Intent = Intent(this, RecipeListActivity::class.java)
                        startActivity(intent)
                    }
                    .show()
            }
        }


//    private fun getRecipeFromIntent(): Recipe {
//        var recipe = Recipe(
//            intent.getStringExtra("id")?.toInt(),
//            intent.getBooleanExtra("isFavorite", false),
//            intent.getStringExtra("title").toString(),
//            intent.getStringExtra("yields").toString(),
//            intent.getStringExtra("prepTime").toString(),
//            intent.getStringExtra("cookTime").toString(),
//            intent.getStringExtra("totalTime").toString(),
//            intent.getStringExtra("ingredients").toString(),
//            intent.getStringExtra("directions").toString(),
//            intent.getStringExtra("imageUrl").toString()
//        )
//        var recipe: List<Recipe> =
//            vm.findRecipeWithTitle(intent.getStringExtra("title").toString())
//        return recipe[0]
//    }

    private fun populateFields(recipe: Recipe) {
        // Map TextViews in recipe page
        var id: TextView = findViewById(R.id.id)
        var title: TextView = findViewById(R.id.title)
        var yields: TextView = findViewById(R.id.yields)
        var prepTime: TextView = findViewById(R.id.prep_time)
        var totalTime: TextView = findViewById(R.id.total_time)
        var ingredients: TextView = findViewById(R.id.ingredients)
        var directions: TextView = findViewById(R.id.directions)
        var recipePhoto: ImageView = findViewById(R.id.image_recipe_photo)
        var iconIsFavorite: ImageView = findViewById(R.id.icon_is_fav)

        id.text = recipe.recipeId.toString()
        title.text = recipe.title
        yields.text = recipe.yields
        prepTime.text = recipe.prepTime
        totalTime.text = recipe.totalTime
        ingredients.text = recipe.ingredients
        directions.text = recipe.directions
        recipePhoto.load(recipe.imageUrl)


        if (recipe.isFavorite) {
            iconIsFavorite.setImageResource(R.drawable.fav_heart_foreground)
        } else {
            iconIsFavorite.setImageResource(R.drawable.fav_heart_gray)
        }
    }

    fun getDataFromDB(repo: RecipeRepository): Recipe {
        // Map TextViews in recipe page
        var id: TextView = findViewById(R.id.id)
        var title: TextView = findViewById(R.id.title)
        var yields: TextView = findViewById(R.id.yields)
        var prepTime: TextView = findViewById(R.id.prep_time)
        var totalTime: TextView = findViewById(R.id.total_time)
        var ingredients: TextView = findViewById(R.id.ingredients)
        var directions: TextView = findViewById(R.id.directions)
        var iconIsFavorite: ImageView = findViewById(R.id.icon_is_fav)

        // Get recipe from repo with ID
        var recipe: List<Recipe> =
            repo.findRecipeWithTitle(intent.getStringExtra("title").toString())

        //TODO: Null validation

//        // Populate Text Views with recipe fields
//        id.text = recipe[0].recipeId.toString()
//        title.text = recipe[0].title
//        yields.text = recipe[0].yields
//        prepTime.text = recipe[0].prepTime
//        totalTime.text = recipe[0].totalTime
//        ingredients.text = recipe[0].ingredients
//        directions.text = recipe[0].directions

        //TODO need to calculate cook time and URL

        return Recipe(
            id.text.toString().toInt(), false, title.text.toString(), yields.text.toString(),
            prepTime.text.toString(), "Need to calculate", totalTime.text.toString(),
            ingredients.text.toString(), directions.text.toString(), "get URL"
        )
    }
}

