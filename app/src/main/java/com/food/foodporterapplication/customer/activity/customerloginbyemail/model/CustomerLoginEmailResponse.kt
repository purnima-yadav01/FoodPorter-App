package com.food.foodporterapplication.customer.activity.customerloginbyemail.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class CustomerLoginEmailResponse {
    @SerializedName("success")
    @Expose
    var success: Boolean? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("token")
    @Expose
    var token: String? = null

    @SerializedName("user")
    @Expose
    var user: User? = null

    inner class User {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("email")
        @Expose
        var email: String? = null

        @SerializedName("role")
        @Expose
        var role: String? = null
    }
}