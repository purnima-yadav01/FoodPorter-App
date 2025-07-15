package com.food.foodporterapplication.customer.activity.addcategoryitemdeatils.addonmeal

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class AddOnMealResponse {
    @SerializedName("success")
    @Expose
    var success: Boolean? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("data")
    @Expose
    var data: Data? = null

    inner class Data {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("image")
        @Expose
        var image: String? = null

        @SerializedName("addon_items")
        @Expose
        var addonItems: List<AddonItem>? = null
    }

    inner class AddonItem {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("max_selection")
        @Expose
        var maxSelection: Int? = null

        @SerializedName("addons")
        @Expose
        var addons: List<Addon>? = null
    }

    inner class Addon {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("price")
        @Expose
        var price: String? = null
    }
}
