package com.food.foodporterapplication.customer.fragment.checkoutorderapi.model

import com.google.gson.annotations.SerializedName

class CheckoutOrderBody (
    @SerializedName("address") val address: String,
    @SerializedName("payment_method") val payment_method: String,
    @SerializedName("cupon_code") val cupon_code: String
        )