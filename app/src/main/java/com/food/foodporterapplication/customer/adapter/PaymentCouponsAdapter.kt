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

class PaymentCouponsAdapter(
    val context: Context,
    private val paymentCouponList: List<ViewAllCouponsResponse.GlobalCoupon>,
) :
    RecyclerView.Adapter<PaymentCouponsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.payment_coupons_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val modelView = paymentCouponList[position]
        val url = modelView.image
        Glide.with(context).load(url).into(holder.couponOfferImg)
        holder.couponTitleText.text = modelView.heading
        holder.couponDescriptionText.text = modelView.saveMessage
        holder.couponCodeText.text = modelView.code

    }

    override fun getItemCount(): Int {
        return paymentCouponList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val couponOfferImg: ImageView = itemView.findViewById(R.id.couponOfferImg)
        val couponTitleText: TextView = itemView.findViewById(R.id.couponTitleText)
        val couponDescriptionText: TextView = itemView.findViewById(R.id.couponDescriptionText)
        val couponCodeText: TextView = itemView.findViewById(R.id.couponCodeText)

    }
}