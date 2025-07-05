package com.food.foodporterapplication.customer.activity.otpverificationapi.model

import com.google.gson.annotations.SerializedName

class OtpVerifyBody (
    @SerializedName("email") val email: String,
    @SerializedName("otp") val otp: String
        )