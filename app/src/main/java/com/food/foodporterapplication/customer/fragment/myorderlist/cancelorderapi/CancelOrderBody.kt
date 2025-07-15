package com.food.foodporterapplication.customer.fragment.myorderlist.cancelorderapi

import com.google.gson.annotations.SerializedName

class CancelOrderBody(
    @SerializedName("order_id") val order_id: Int,
    @SerializedName("cancel_reason ") val cancel_reason: String
)