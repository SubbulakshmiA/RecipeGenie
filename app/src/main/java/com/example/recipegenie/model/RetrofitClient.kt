package com.example.recipegenie.model

import android.os.Build.HOST
import com.example.recipegenie.BuildConfig
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RetrofitClient {

    @Headers(
        "X-RapidAPI-Host: tasty.p.rapidapi.com",
        "X-RapidAPI-Key: a2422d4466msh13115e1307570b7p10e5acjsnd3a59dcf56f0"
    )
    @GET("recipes/list")
    suspend fun getSearchResults(
        @Query("from") offset: Int, @Query("size") limit: Int,
        @Query("tags") tags: String, @Query("q") search: String
    ): Response<RecipeResults>

    companion object {
        var BASE_URL = "https://tasty.p.rapidapi.com/"

        fun create(): RetrofitClient {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(RetrofitClient::class.java)
        }
    }
}
