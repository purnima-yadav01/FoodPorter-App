package com.food.foodporterapplication.customer.activity.addnewaddressapi.savedaddressapi

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class SavedAddressResponse {
    @SerializedName("success")
    @Expose
    var success: Boolean? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("total")
    @Expose
    var total: Int? = null

    @SerializedName("Data")
    @Expose
    var data: List<Datum>? = null

    inner class Datum{

        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("user_id")
        @Expose
        var userId: Int? = null

        @SerializedName("firstName")
        @Expose
        var firstName: String? = null

        @SerializedName("lastName")
        @Expose
        var lastName: String? = null

        @SerializedName("phone")
        @Expose
        var phone: String? = null

        @SerializedName("country_code")
        @Expose
        var countryCode: String? = null

        @SerializedName("address_line")
        @Expose
        var addressLine: String? = null

        @SerializedName("landmark")
        @Expose
        var landmark: String? = null

        @SerializedName("city")
        @Expose
        var city: String? = null

        @SerializedName("state")
        @Expose
        var state: String? = null

        @SerializedName("street")
        @Expose
        var street: Any? = null

        @SerializedName("buildingName")
        @Expose
        var buildingName: Any? = null

        @SerializedName("pincode")
        @Expose
        var pincode: String? = null

        @SerializedName("country")
        @Expose
        var country: String? = null

        @SerializedName("latitude")
        @Expose
        var latitude: Any? = null

        @SerializedName("longitude")
        @Expose
        var longitude: Any? = null

        @SerializedName("type")
        @Expose
        var type: String? = null

        @SerializedName("is_default")
        @Expose
        var isDefault: Int? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null
    }
}