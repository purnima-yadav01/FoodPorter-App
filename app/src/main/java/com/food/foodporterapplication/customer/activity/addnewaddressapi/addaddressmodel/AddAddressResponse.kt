package com.food.foodporterapplication.customer.activity.addnewaddressapi.addaddressmodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AddAddressResponse {
    @SerializedName("success")
    @Expose
    var success: Boolean? = null

    @SerializedName("message")
    @Expose
    var message: String? = null
}