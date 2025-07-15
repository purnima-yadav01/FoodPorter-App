package com.food.foodporterapplication.customer.activity.addcategoryitemdeatils.updatecartapi

import com.google.gson.annotations.SerializedName

class UpdateQuantityBody(
    @SerializedName("dish_id") val dish_id: String,
    @SerializedName("quantity") val quantity: String
        )