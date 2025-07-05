package com.food.foodporterapplication.customer.activity.applycouponsapi.viewallcoupons

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class ViewAllCouponsResponse {
    @SerializedName("success")
    @Expose
    var success: Boolean? = null

    @SerializedName("restaurantCoupons")
    @Expose
    var restaurantCoupons: List<RestaurantCoupon>? = null

    @SerializedName("globalCoupons")
    @Expose
    var globalCoupons: List<GlobalCoupon>? = null

    inner class RestaurantCoupon{
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("code")
        @Expose
        var code: String? = null

        @SerializedName("heading")
        @Expose
        var heading: String? = null

        @SerializedName("image")
        @Expose
        var image: String? = null

        @SerializedName("min_order_amount")
        @Expose
        var minOrderAmount: String? = null

        @SerializedName("saveMessage")
        @Expose
        var saveMessage: String? = null
    }
    inner class GlobalCoupon{
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("code")
        @Expose
        var code: String? = null

        @SerializedName("heading")
        @Expose
        var heading: String? = null

        @SerializedName("image")
        @Expose
        var image: String? = null

        @SerializedName("min_order_amount")
        @Expose
        var minOrderAmount: String? = null

        @SerializedName("saveMessage")
        @Expose
        var saveMessage: String? = null
    }
}