package com.food.foodporterapplication.customer.activity.addtocartitemapi.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class AddToCartResponse {

    @SerializedName("success")
    @Expose
    var success: Boolean? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("cart_item_id")
    @Expose
    var cartItemId: Int? = null

    @SerializedName("total_quantity")
    @Expose
    var totalQuantity: Int? = null

    @SerializedName("addons_added")
    @Expose
    var addonsAdded: List<Int>? = null
}