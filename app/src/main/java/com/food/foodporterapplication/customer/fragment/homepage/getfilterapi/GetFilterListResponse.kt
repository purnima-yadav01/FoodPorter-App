package com.food.foodporterapplication.customer.fragment.homepage.getfilterapi

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class GetFilterListResponse {

    @SerializedName("success")
    @Expose
    var success: Boolean? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("filters")
    @Expose
    var filters: List<Filter>? = null

    inner class Filter {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("image")
        @Expose
        var image: String? = null

        @SerializedName("type")
        @Expose
        var type: String? = null
    }
}