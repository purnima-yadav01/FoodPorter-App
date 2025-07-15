package com.food.foodporterapplication.customer.activity.addtocartitemapi.model

import com.google.gson.annotations.SerializedName

class AddToCartItemBody (
    @SerializedName("dish_id") val dish_id: Int,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("addons_ids") val addons_ids: List<Int>,
        )