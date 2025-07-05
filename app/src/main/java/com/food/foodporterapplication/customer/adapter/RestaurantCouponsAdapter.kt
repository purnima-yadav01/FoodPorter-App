package com.food.foodporterapplication.customer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.activity.applycouponsapi.viewallcoupons.ViewAllCouponsResponse

class RestaurantCouponsAdapter (
    val context: Context,
    private val restCouponList: List<ViewAllCouponsResponse.RestaurantCoupon>,
) :
    RecyclerView.Adapter<RestaurantCouponsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.restaurant_coupon_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val modelView = restCouponList[position]
        val url = modelView.image
        Glide.with(context).load(url).into(holder.restCouponImg)
        holder.restCouponHeading.text = modelView.heading
        holder.restCouponTextview.text = modelView.saveMessage
        holder.restCouponCode.text = modelView.code

    }

    override fun getItemCount(): Int {
        return restCouponList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val restCouponImg: ImageView = itemView.findViewById(R.id.restCouponImg)
        val restCouponHeading: TextView = itemView.findViewById(R.id.restCouponHeading)
        val restCouponTextview: TextView = itemView.findViewById(R.id.restCouponTextview)
        val restCouponCode: TextView = itemView.findViewById(R.id.restCouponCode)

    }
}