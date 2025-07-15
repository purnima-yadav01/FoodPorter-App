package com.food.foodporterapplication.customer.fragment.myorderlist.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class MyOrderListResponse {
    @SerializedName("success")
    @Expose
    var success: Boolean? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("orders")
    @Expose
    var orders: List<Order>? = null

    class Order {
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

        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null

        @SerializedName("order_date")
        @Expose
        var orderDate: String? = null

        @SerializedName("order_time")
        @Expose
        var orderTime: String? = null

        @SerializedName("restaurant_name")
        @Expose
        var restaurantName: String? = null

        @SerializedName("address")
        @Expose
        var address: String? = null

        @SerializedName("restaurant_image_url")
        @Expose
        var restaurantImageUrl: Any? = null

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

        @SerializedName("order_status")
        @Expose
        var orderStatus: String? = null

        @SerializedName("image_url")
        @Expose
        var imageUrl: String? = null

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