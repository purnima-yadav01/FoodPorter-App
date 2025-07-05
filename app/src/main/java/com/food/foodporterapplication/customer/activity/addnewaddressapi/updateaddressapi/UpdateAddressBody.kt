package com.food.foodporterapplication.customer.activity.addnewaddressapi.updateaddressapi

import com.google.gson.annotations.SerializedName

class UpdateAddressBody(
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("country_code") val country_code: String,
    @SerializedName("address_line") val address_line: String,
    @SerializedName("landmark") val landmark: String,
    @SerializedName("city") val city: String,
    @SerializedName("buildingName") val buildingName: String,
    @SerializedName("street") val street: String,
    @SerializedName("state") val state: String,
    @SerializedName("pincode") val pincode: String,
    @SerializedName("country") val country: String,
    @SerializedName("type") val type: String,
    @SerializedName("is_default") val is_default: Int,

    )