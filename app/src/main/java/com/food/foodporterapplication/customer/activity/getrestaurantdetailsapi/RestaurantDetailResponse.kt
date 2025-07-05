package com.food.foodporterapplication.customer.activity.getrestaurantdetailsapi

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class RestaurantDetailResponse {
    @SerializedName("success")
    @Expose
    var success: Boolean? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("data")
    @Expose
    var data: List<Datum>? = null

 inner class Datum{
     @SerializedName("id")
     @Expose
     var id: Int? = null

     @SerializedName("category_id")
     @Expose
     var categoryId: Int? = null

     @SerializedName("restaurant_id")
     @Expose
     var restaurantId: Int? = null

     @SerializedName("name")
     @Expose
     var name: String? = null

     @SerializedName("description")
     @Expose
     var description: String? = null

     @SerializedName("price")
     @Expose
     var price: String? = null

     @SerializedName("image")
     @Expose
     var image: String? = null

     @SerializedName("is_available")
     @Expose
     var isAvailable: Int? = null

     @SerializedName("created_at")
     @Expose
     var createdAt: String? = null

     @SerializedName("isTrending")
     @Expose
     var isTrending: Int? = null
 }
}