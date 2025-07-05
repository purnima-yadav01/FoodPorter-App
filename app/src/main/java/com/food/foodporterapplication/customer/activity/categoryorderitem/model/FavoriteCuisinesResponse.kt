package com.food.foodporterapplication.customer.activity.categoryorderitem.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class FavoriteCuisinesResponse {
    @SerializedName("success")
    @Expose
    var success: Boolean? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("data")
    @Expose
    var data: List<Datum>? = null

    inner class Datum {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("image")
        @Expose
        var image: String? = null

        @SerializedName("address")
        @Expose
        var address: String? = null

        @SerializedName("rating")
        @Expose
        var rating: Double? = null

        @SerializedName("discount")
        @Expose
        var discount: String? = null

        @SerializedName("estimated_delivery_time")
        @Expose
        var estimatedDeliveryTime: String? = null

        @SerializedName("starting_price")
        @Expose
        var startingPrice: String? = null

        @SerializedName("free_delivery")
        @Expose
        var freeDelivery: Boolean? = null

    }
}