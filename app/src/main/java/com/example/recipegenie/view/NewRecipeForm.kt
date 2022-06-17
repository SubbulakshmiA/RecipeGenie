package com.example.recipegenie.view

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.recipegenie.R
import com.example.recipegenie.model.AppDatabase
import com.example.recipegenie.model.Recipe
import com.example.recipegenie.model.RecipeRepository
import com.example.recipegenie.viewmodel.MainViewModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class NewRecipeForm : AppCompatActivity() {

    lateinit var vm: MainViewModel

    private lateinit var inputTitle: EditText
    private lateinit var inputYields: EditText
    private lateinit var inputPrepTime: EditText
    private lateinit var inputCookTime: EditText
    private lateinit var inputIngredients: EditText
    private lateinit var inputDirections: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_recipe_form)

        inputTitle = findViewById(R.id.edit_text_title)
        inputYields = findViewById(R.id.edit_text_yield)
        inputPrepTime = findViewById(R.id.edit_text_prep_time)
        inputCookTime = findViewById(R.id.edit_text_cook_time)
        inputIngredients = findViewById(R.id.edit_text_ingredients)
        inputDirections = findViewById(R.id.edit_text_directions)

        var dao = AppDatabase.getInstance(this)?.recipeDao()!!
        var repo = RecipeRepository(dao)

        vm = MainViewModel(repo)
        val btnSubmit: ExtendedFloatingActionButton = findViewById(R.id.btn_add)
        val btnCancel: ExtendedFloatingActionButton = findViewById(R.id.btn_cancel)

        btnSubmit.setOnClickListener {
            val title: String = inputTitle.text.toString()
            val yields: String = inputYields.text.toString() + "servings"
            val prepTime: String = inputPrepTime.text.toString() + "minutes"
            val cookTime: String = inputCookTime.text.toString() + "minutes"
            val totalTime: String = "${inputPrepTime.text.toString().toInt() + inputCookTime.text.toString().toInt()} minutes"
            val ingredients: String = inputIngredients.text.toString()
            val directions: String = inputDirections.text.toString()


            // Form validation

                if (title.isNullOrBlank() || yields.isNullOrBlank() || prepTime.isNullOrBlank() ||
                    cookTime.isNullOrBlank() || ingredients.isNullOrBlank() ||
                    directions.isNullOrBlank()
                ) {
                    if (title.isNullOrBlank()) {
                        this.inputTitle.setError("Please Enter a recipe title")
                    }
                    if (yields.isNullOrBlank()) {
                        this.inputYields.setError("Please Enter number of servings")
                    }
                    if (prepTime.isNullOrBlank()) {
                        this.inputPrepTime.setError("Please Enter time it takes to prep ingredients")
                    }
                    if (cookTime.isNullOrBlank()) {
                        this.inputCookTime.setError("Please Enter time it takes to cook")
                    }
                    if (ingredients.isNullOrBlank()) {
                        this.inputIngredients.setError("Please the ingredient list")
                    }
                    if (directions.isNullOrBlank()) {
                        this.inputDirections.setError("Please Enter cooking steps")
                    }
                    val builder = AlertDialog.Builder(this)
                    // Set Alert Title
                    builder.setTitle("All fields are required")
                    // Set the message show for the Alert time
                    builder.setMessage("Please complete the form")
                    builder.setCancelable(true)
                    builder.setNegativeButton("OK", DialogInterface.OnClickListener
                    { dialog, which -> dialog.cancel() })

                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.show()

                }else{
//
                    val recipe = Recipe(
                        null,
                        true,
                        title,
                        yields,
                        prepTime,
                        cookTime,
                        totalTime,
                        ingredients,
                        directions,
                        "https://img.buzzfeed.com/thumbnailer-prod-us-east-1/" +
                    "video-api/assets/351171.jpg"
                    )

                    vm.insertRecipes(recipe)

                    val intent = Intent(this, RecipeListActivity::class.java)
                    intent.putExtra("title", recipe.title.toString())
                    startActivity(intent)

                    val message = "recipe successfully added"
                    val duration = Toast.LENGTH_LONG
                    val toast = Toast.makeText(applicationContext, message, duration)
                    toast.show()
            }
        }

        // Cancel button
        btnCancel.setOnClickListener {
            val intent = Intent(this, RecipeListActivity::class.java)
            startActivity(intent)

            val message = "cancelled"
            val duration = Toast.LENGTH_LONG
            val toast = Toast.makeText(applicationContext, message, duration)
            toast.show()
        }
    }
}