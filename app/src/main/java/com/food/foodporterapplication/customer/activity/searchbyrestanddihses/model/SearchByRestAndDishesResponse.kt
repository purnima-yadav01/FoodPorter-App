package com.food.foodporterapplication.customer.activity.searchbyrestanddihses.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class SearchByRestAndDishesResponse {

    @SerializedName("success")
    @Expose
    var success: Boolean? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("data")
    @Expose
    var data: Data? = null

    class Data {
        @SerializedName("restaurants")
        @Expose
        var restaurants: List<Any>? = null

        @SerializedName("dishes")
        @Expose
        var dishes: List<Dish>? = null
    }

    class Dish {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("image")
        @Expose
        var image: String? = null

        @SerializedName("price")
        @Expose
        var price: String? = null
    }
}
