package com.food.foodporterapplication.customer.activity.otpverificationapi.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class OtpVerifyResponse {
    @SerializedName("success")
    @Expose
    var success: Boolean? = null

    @SerializedName("message")
    @Expose
    var message: String? = null
}