package com.food.foodporterapplication.customer.activity.confirmpasswordapi.model

import com.google.gson.annotations.SerializedName

class ConfirmPasswordBody (
    @SerializedName("email") val email: String,
    @SerializedName("newPassword ") val newPassword : String,
    @SerializedName("confirmPassword ") val confirmPassword : String
        )