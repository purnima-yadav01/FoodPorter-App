package com.food.foodporterapplication.customer.fragment.cartItemDetail.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class CardItemDetailResponse {

    @SerializedName("success")
    @Expose
    var success: Boolean? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("data")
    @Expose
    var data: Data? = null

    class Data {

        @SerializedName("cartItems")
        @Expose
        var cartItems: List<CartItem>? = null

        @SerializedName("subtotal")
        @Expose
        var subtotal: Int? = null

        @SerializedName("discount")
        @Expose
        var discount: Int? = null

        @SerializedName("delivery_charge")
        @Expose
        var deliveryCharge: Int? = null

        @SerializedName("total")
        @Expose
        var total: Int? = null

        @SerializedName("coupon")
        @Expose
        var coupon: Any? = null

        @SerializedName("you_saved")
        @Expose
        var youSaved: Int? = null
    }

    class CartItem {

        @SerializedName("dish_id")
        @Expose
        var dishId: Int? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("quantity")
        @Expose
        var quantity: Int? = null

        @SerializedName("price")
        @Expose
        var price: String? = null

        @SerializedName("total")
        @Expose
        var total: Int? = null

        @SerializedName("image")
        @Expose
        var image: String? = null
    }
}
