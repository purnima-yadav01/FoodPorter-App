package com.food.foodporterapplication.customer.fragment.homepage.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class GetAllRestaurantResponse {
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

        @SerializedName("phone")
        @Expose
        var phone: String? = null

        @SerializedName("deliverey_time")
        @Expose
        var delivereyTime: String? = null

        @SerializedName("offer")
        @Expose
        var offer: String? = null

        @SerializedName("rating")
        @Expose
        var rating: Double? = null

        @SerializedName("city")
        @Expose
        var city: String? = null

        @SerializedName("status")
        @Expose
        var status: String? = null

        @SerializedName("image")
        @Expose
        var image: String? = null
    }

}