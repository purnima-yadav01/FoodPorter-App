package com.food.foodporterapplication.customer.fragment.homepage.getallcategory

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetAllCategoryResponse {

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

        @SerializedName("delivery_time_range")
        @Expose
        var deliveryTimeRange: String? = null

    }
}