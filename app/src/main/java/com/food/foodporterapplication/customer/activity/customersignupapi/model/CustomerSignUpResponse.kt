package com.food.foodporterapplication.customer.activity.customersignupapi.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class CustomerSignUpResponse {
    @SerializedName("success")
    @Expose
    var success: Boolean? = null

    @SerializedName("message")
    @Expose
    var message: String? = null
}