package com.example.recipegenie.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipegenie.MainActivity
import com.example.recipegenie.R
import com.example.recipegenie.model.AppDatabase
import com.example.recipegenie.model.Recipe
import com.example.recipegenie.model.RecipeRepository
import com.example.recipegenie.viewmodel.MainViewModel
import com.example.recipegenie.adapters.RecipeListAdapter
import com.example.recipegenie.viewmodel.RecipeListGenerator

class SearchRecipes : AppCompatActivity() {

    var recipeList = ArrayList<Recipe>()
    lateinit var mainViewModel: MainViewModel
    lateinit var recipeAdapter: RecipeListAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_recipes)

        var navBtnFavorites : View = findViewById(R.id.nav_btn_favorites)
        var navBntHome : View = findViewById(R.id.nav_btn_home)
        var navBtnAdd : View = findViewById(R.id.nav_btn_add)
        progressBar = findViewById(R.id.progress_bar)

        var dao = AppDatabase.getInstance(this)?.recipeDao()!!
        var repo = RecipeRepository(dao)

        mainViewModel = MainViewModel(repo)
        recyclerView = findViewById(R.id.recyclerView_favorites_card)

        mainViewModel.getSearchResults(0, 20, "", "")
        var mutableLiveData = mainViewModel.searchResults

        mutableLiveData.observe(this){
            var recipeListGenerator = RecipeListGenerator()
            recipeList = recipeListGenerator.makeList(it)
            //getRecipes(apiRecipeList)
            recipeAdapter.setItems(recipeList)
            progressBar.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }

        recipeAdapter = RecipeListAdapter(recipeList, { position -> onCardClick(position) })

        recyclerView.layoutManager = LinearLayoutManager(this)


        var searchView : SearchView = findViewById(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                mainViewModel.getSearchResults(0, 50, "", query!!)
                var mutableLiveData = mainViewModel.searchResults

                mutableLiveData.observe(this@SearchRecipes){
                    var recipeListGenerator = RecipeListGenerator()
                    recipeList = recipeListGenerator.makeList(it)
                    //getRecipes(apiRecipeList)
                    recipeAdapter.setItems(recipeList)
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        recyclerView.adapter = recipeAdapter

//        var listGenerator = RecipeListGenerator()
//
//        mainViewModel.searchResults.observe(this){
//            recipeList = listGenerator.makeList(it)
//        }

        // create an adapter
       // recipeAdapter = RecipeAdapter({ position -> onCardClick(position) }, recipeList)


        // take the views adapter then assign it to the custom adapter we created
        //recyclerView.adapter = recipeAdapter

        navBtnFavorites.setOnClickListener {
            val myIntent = Intent(this, RecipeListActivity::class.java)
            startActivity(myIntent)
        }
        navBntHome.setOnClickListener {
            val myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)
        }

        navBtnAdd.setOnClickListener {
            val myIntent = Intent(this, NewRecipeForm::class.java)
            startActivity(myIntent)
        }
    }

    private fun onCardClick(position: Int) {
        val myIntent = Intent(this, RecipeDetails::class.java)
        myIntent.putExtra("id", recipeList[position].recipeId)
        myIntent.putExtra("isFavorite", recipeList[position].isFavorite)
        myIntent.putExtra("title", recipeList[position].title)
        myIntent.putExtra("yields", recipeList[position].yields)
        myIntent.putExtra("prepTime", recipeList[position].prepTime)
        myIntent.putExtra("cookTime", recipeList[position].cookTime)
        myIntent.putExtra("totalTime", recipeList[position].totalTime)
        myIntent.putExtra("ingredients", recipeList[position].ingredients)
        myIntent.putExtra("directions", recipeList[position].directions)
        myIntent.putExtra("imageUrl", recipeList[position].imageUrl)

        startActivity(myIntent)
    }
    private fun getRecipes(recipeList: List<Recipe>) {
        this.recipeList.clear()
        this.recipeList.addAll(recipeList)
        recipeAdapter.notifyDataSetChanged()
    }

}