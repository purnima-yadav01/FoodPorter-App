package com.food.foodporterapplication.customer.activity.addnewaddressapi.updateaddressapi

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UpdateAddressResponse {
    @SerializedName("success")
    @Expose
    var success: Boolean? = null

    @SerializedName("message")
    @Expose
    var message: String? = null
}