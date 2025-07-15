package com.food.foodporterapplication.customer.fragment.myorderlist.cancelorderapi

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class CancelOrderResponse {

    @SerializedName("success")
    @Expose
    var success: Boolean? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("order_id")
    @Expose
    var orderId: Int? = null
}