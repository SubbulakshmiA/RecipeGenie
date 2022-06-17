package com.example.recipegenie.model

// This is the pojo that contains the search resultsgit

import java.util.*
import kotlin.collections.ArrayList

data class RecipeResults(
    var count: Int,
    var results: ArrayList<Results>
)

data class Results(

    var keywords: String,
    var updated_at: Long,
    var topics: ArrayList<Topics>,
    var original_video_url: String?,
    var is_shoppable: Boolean,
    var nutrition_visibility: String,
    var id: Int,
    var servings_noun_singular: String,
    var tags: ArrayList<Tags>,
    var buzz_id: String?,
    var servings_noun_plural: String,
    var renditions: ArrayList<Renditions>,
    var total_time_tier: TotalTimeTier,
    var country: String,
    var instructions: ArrayList<Instructions>,
    var nutrition: Nutrition,
    var aspect_ratio: String,
    var thumbnail_url: String,
    var canonical_id: String,
    var video_id: Int,
    var cook_time_minutes: Int,
    var facebook_posts: ArrayList<String?>,
    var show_id: Int,
    var sections: ArrayList<Sections>,
    var brand_id: String?,
    var tips_and_ratings_enabled: Boolean,
    var credits: ArrayList<Credits>,
    var is_one_top: Boolean,
    var promotion: String,
    var video_ad_content: String,
    var user_ratings: UserRatings,
    var name: String,
    var show: Show,
    var description: String,
    var total_time_minutes: Int,
    var approved_at: Long,
    var beauty_url: String?,
    var brand: Brand?,
    var compilations: ArrayList<Objects?>,
    var thumbnail_alt_text: String?,
    var video_url: String?,
    var yields: String,
    var language: String,
    var slug: String,
    var num_servings: Int,
    var created_at: Long,
    var draft_status: String,
    var seo_title: String?,
    var prep_time_minutes: Int,
    var inspired_by_url: String?,
)

data class Brand (
    var image_url : String,
    var name : String,
    var id : Int,
    var slug : String
)

data class Renditions(
    var file_size: Long,
    var duration: Int,
    var width: Int,
    var minimum_bit_rate: String?,
    var name: String,
    var maximum_bit_rate: String?,
    var container: String,
    var url: String?,
    var bit_rate: Int,
    var content_type: String,
    var aspect: String,
    var height: Int,
    var poster_url: String,
)

data class Topics(
    var name: String,
    var slug: String
)

data class Tags(
    var display_name: String,
    var type: String,
    var name: String,
    var id: Int
)

data class TotalTimeTier(
    var tier: String,
    var display_tier: String,
)

data class Instructions(
    var appliance: String?,
    var end_time: Long,
    var temperature: Int?,
    var id: Int,
    var position: Int,
    var display_text: String,
    var start_time: Int
)

data class Nutrition(
    var carbohydrates: Int,
    var fiber: Int,
    var updated_at: String,
    var protein: Int,
    var fat: Int,
    var calories: Int,
    var sugar: Int,
)

data class Sections(
    var components: ArrayList<Components>,
    var name: String,
    var position: Int,
)

data class Components(
    var id: Int,
    var position: Int,
    var measurements: ArrayList<Measurements>,
    var raw_text: String,
    var extra_comment: String,
    var ingredient: Ingredient,
)

data class Measurements(
    var id: Int,
    var unit: Units,
    var quantity: String,
)

data class Units(
    var abbreviation: String,
    var system: String,
    var name: String,
    var display_plural: String,
    var display_singular: String
)

data class Ingredient(
    var display_singular: String,
    var updated_at: Long,
    var name: String,
    var created_at: Long,
    var display_plural: String,
    var id: Int,
)

data class Credits(
    var name: String,
    var type: String,
)

data class UserRatings(
    var count_positive: Int,
    var score: Double,
    var count_negative: Int,
)

data class Show(
    var name: String,
    var id: Int,
)
