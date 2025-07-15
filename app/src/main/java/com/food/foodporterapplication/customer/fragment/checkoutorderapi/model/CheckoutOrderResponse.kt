package com.food.foodporterapplication.customer.fragment.checkoutorderapi.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class CheckoutOrderResponse {

    @SerializedName("success")
    @Expose
    var success: Boolean? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("orders")
    @Expose
    var orders: List<Order>? = null

    class Order{
        @SerializedName("order_id")
        @Expose
        var orderId: Int? = null

        @SerializedName("restaurant_id")
        @Expose
        var restaurantId: String? = null

        @SerializedName("subtotal")
        @Expose
        var subtotal: Int? = null

        @SerializedName("restaurantDiscount")
        @Expose
        var restaurantDiscount: Double? = null

        @SerializedName("couponDiscount")
        @Expose
        var couponDiscount: Int? = null

        @SerializedName("finalAmount")
        @Expose
        var finalAmount: Double? = null
    }
}