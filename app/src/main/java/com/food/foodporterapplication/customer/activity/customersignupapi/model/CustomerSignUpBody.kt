package com.food.foodporterapplication.customer.activity.customersignupapi.model

import com.google.gson.annotations.SerializedName

class CustomerSignUpBody (
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("email") val email: String,
    @SerializedName("countryCode") val countryCode: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("password") val password: String,
    @SerializedName("role") val role: String,

        )