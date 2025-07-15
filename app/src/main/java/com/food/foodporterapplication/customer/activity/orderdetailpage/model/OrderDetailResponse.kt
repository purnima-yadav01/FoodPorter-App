package com.food.foodporterapplication.customer.activity.orderdetailpage.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class OrderDetailResponse {
    @SerializedName("success")
    @Expose
    var success: Boolean? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("order")
    @Expose
    var order: Order? = null

    class Order{

        @SerializedName("order_id")
        @Expose
        var orderId: Int? = null

        @SerializedName("restaurant_id")
        @Expose
        var restaurantId: Int? = null

        @SerializedName("total_amount")
        @Expose
        var totalAmount: String? = null

        @SerializedName("discount")
        @Expose
        var discount: String? = null

        @SerializedName("final_amount")
        @Expose
        var finalAmount: String? = null

        @SerializedName("payment_method")
        @Expose
        var paymentMethod: String? = null

        @SerializedName("address")
        @Expose
        var address: String? = null

        @SerializedName("order_date")
        @Expose
        var orderDate: String? = null

        @SerializedName("order_time")
        @Expose
        var orderTime: String? = null

        @SerializedName("restaurant_name")
        @Expose
        var restaurantName: String? = null

        @SerializedName("restaurant_address")
        @Expose
        var restaurantAddress: String? = null

        @SerializedName("restaurant_image_url")
        @Expose
        var restaurantImageUrl: String? = null

        @SerializedName("items")
        @Expose
        var items: List<Item>? = null

    }

    class Item {
        @SerializedName("order_item_id")
        @Expose
        var orderItemId: Int? = null

        @SerializedName("dish_id")
        @Expose
        var dishId: Int? = null

        @SerializedName("dish_name")
        @Expose
        var dishName: String? = null

        @SerializedName("quantity")
        @Expose
        var quantity: Int? = null

        @SerializedName("price")
        @Expose
        var price: String? = null

        @SerializedName("rating")
        @Expose
        var rating: Any? = null

        @SerializedName("order_status")
        @Expose
        var orderStatus: String? = null

        @SerializedName("dish_image_url")
        @Expose
        var dishImageUrl: String? = null

        @SerializedName("addons")
        @Expose
        var addons: List<Addon>? = null
    }

    class Addon{
        @SerializedName("addon_id")
        @Expose
        var addonId: Int? = null

        @SerializedName("addon_name")
        @Expose
        var addonName: String? = null

        @SerializedName("addon_price")
        @Expose
        var addonPrice: String? = null

        @SerializedName("quantity")
        @Expose
        var quantity: Int? = null

    }
}