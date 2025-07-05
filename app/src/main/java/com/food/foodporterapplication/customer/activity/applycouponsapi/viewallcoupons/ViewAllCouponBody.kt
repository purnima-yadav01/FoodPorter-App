package com.food.foodporterapplication.customer.activity.applycouponsapi.viewallcoupons

import com.google.gson.annotations.SerializedName

class ViewAllCouponBody(
    @SerializedName("restaurant_id") val restaurant_id: Int
)