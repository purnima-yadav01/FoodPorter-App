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

    @SerializedName("cart")
    @Expose
    var cart: Cart? = null

    class Cart {

        @SerializedName("items")
        @Expose
        var items: List<Item>? = null

        @SerializedName("subTotal")
        @Expose
        var subTotal: Int? = null

        @SerializedName("standard_delivery")
        @Expose
        var standardDelivery: Int? = null

        @SerializedName("total_price")
        @Expose
        var totalPrice: Int? = null
    }

    class Item {

        @SerializedName("cart_id")
        @Expose
        var cartId: Int? = null

        @SerializedName("dish_id")
        @Expose
        var dishId: Int? = null

        @SerializedName("dish_name")
        @Expose
        var dishName: String? = null

        @SerializedName("dish_price")
        @Expose
        var dishPrice: String? = null

        @SerializedName("quantity")
        @Expose
        var quantity: Int? = null

        @SerializedName("image_url")
        @Expose
        var imageUrl: String? = null

        @SerializedName("addons")
        @Expose
        var addons: List<Addon>? = null

        @SerializedName("total_price")
        @Expose
        var totalPrice: Int? = null
    }

    class Addon {

        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("price")
        @Expose
        var price: String? = null
    }
}
