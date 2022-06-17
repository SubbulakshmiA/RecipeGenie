package com.example.recipegenie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipegenie.model.AppDatabase
import com.example.recipegenie.model.Recipe
import com.example.recipegenie.model.RecipeRepository
import com.example.recipegenie.model.RetrofitClient
import com.example.recipegenie.view.LandingPage
import com.example.recipegenie.view.RecipeDetails
import com.example.recipegenie.view.RecipeListActivity
import com.example.recipegenie.view.SearchRecipes
import com.example.recipegenie.adapters.MainActivityAdapter
import com.example.recipegenie.adapters.MyAdapter
import com.example.recipegenie.viewmodel.MainViewModel
import com.example.recipegenie.viewmodel.RecipeListGenerator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    var apiRecipeList = ArrayList<Recipe>()
    lateinit var auth : FirebaseAuth
    var databaseReference : DatabaseReference? =null
    var database : FirebaseDatabase? = null
    lateinit var adapterCategories: MainActivityAdapter
    lateinit var adapterMyList: MyAdapter
    lateinit var sign_out_btn: TextView
    lateinit var breakfast: Button
    lateinit var lunch: Button
    lateinit var dinner: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var inter = RetrofitClient.create()
        var dao = AppDatabase.getInstance(this)?.recipeDao()!!
        var repo = RecipeRepository(dao)

//        var recipe = Recipe(
//            null,
//            true,
//            "egg",
//            "6 servings",
//            "10 mins",
//            "35 mins",
//            "45 mins",
//            "1 whole chicken\n" +
//                    "1/2 Onion\n" +
//                    "3 tomatoes\n" +
//                    "2 potatoes\n" +
//                    "3 carrorts\n" +
//                    "salt and pepper",
//            "Step 1: Prep the chicken\n" +
//                    "Step 2: Prep the veggies\n" +
//                    "Step 3: prep the pot\n" +
//                    "Step 4: Cook it well",
//            "https://img.buzzfeed.com/thumbnailer-prod-us-east-1/" +
//                    "video-api/assets/351171.jpg"
//        )

        var vm = MainViewModel(repo)

        breakfast = findViewById(R.id.breakfast)
        lunch = findViewById(R.id.lunch)
        dinner = findViewById(R.id.dinner)

        populateFavoritesView(vm, breakfast, lunch, dinner)
        populateCategoriesView(vm)



        var navBtnSearch: View = findViewById(R.id.nav_btn_search)
        var navBtnHome: View = findViewById(R.id.nav_btn_home)
        var navBtnFavorites: View = findViewById(R.id.nav_btn_favorites)
        sign_out_btn = findViewById(R.id.sign_out_btn)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        sign_out_btn.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LandingPage::class.java))
            finish()
        }

        navBtnSearch.setOnClickListener {
            val myIntent = Intent(this, SearchRecipes::class.java)
            startActivity(myIntent)
        }
        navBtnHome.setOnClickListener {
            val myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)
        }

        navBtnFavorites.setOnClickListener {
            val myIntent = Intent(this, RecipeListActivity::class.java)
            myIntent.putExtra("listSource", "Favorites")
            startActivity(myIntent)
        }
    }

    private fun populateFavoritesView(viewModel: MainViewModel, breakfast:Button, lunch: Button, dinner: Button) {

//        this.recipeList.clear()

        viewModel.getAllRecipes()
        var liveData = viewModel.recipeList
        liveData?.observe(this){
            adapterMyList.setItems(it)
        }

        adapterMyList = MyAdapter(apiRecipeList, { position -> onCardClick(position) })
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView_favorites)
        recyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerView.adapter = adapterMyList
    }

    fun populateCategoriesView(viewModel: MainViewModel){
        viewModel.getSearchResults(0, 20, "", "")
        breakfast.setOnClickListener(){
            viewModel.getSearchResults(0, 20, "breakfast", "")
        }
        lunch.setOnClickListener(){
            viewModel.getSearchResults(0, 20, "lunch", "")
        }
        dinner.setOnClickListener(){
            viewModel.getSearchResults(0, 20, "dinner", "")
        }

        var mutableLiveData = viewModel.searchResults

        mutableLiveData.observe(this){
            var recipeListGenerator = RecipeListGenerator()
            apiRecipeList = recipeListGenerator.makeList(it)
            //getRecipes(apiRecipeList)
            adapterCategories.setItems(apiRecipeList)
        }

        adapterCategories = MainActivityAdapter(apiRecipeList, { position -> onCardClick(position) })

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView_category)
        recyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerView.adapter = adapterCategories
    }

    private fun onCardClick(position: Int) {
        val myIntent = Intent(this, RecipeDetails::class.java)
        myIntent.putExtra("id", apiRecipeList[position].recipeId)
        myIntent.putExtra("isFavorite", apiRecipeList[position].isFavorite)
        myIntent.putExtra("title", apiRecipeList[position].title)
        myIntent.putExtra("yields", apiRecipeList[position].yields)
        myIntent.putExtra("prepTime", apiRecipeList[position].prepTime)
        myIntent.putExtra("cookTime", apiRecipeList[position].cookTime)
        myIntent.putExtra("totalTime", apiRecipeList[position].totalTime)
        myIntent.putExtra("ingredients", apiRecipeList[position].ingredients)
        myIntent.putExtra("directions", apiRecipeList[position].directions)
        myIntent.putExtra("imageUrl", apiRecipeList[position].imageUrl)

        startActivity(myIntent)
    }

    private fun getRecipes(recipeList: List<Recipe>) {
        this.apiRecipeList.clear()
        this.apiRecipeList.addAll(recipeList)
        adapterMyList.notifyDataSetChanged()
    }
}

