package com.food.foodporterapplication.customer.activity.customerloginbyemail.model

import com.google.gson.annotations.SerializedName

class CustomerLoginEmailBody (
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
        )