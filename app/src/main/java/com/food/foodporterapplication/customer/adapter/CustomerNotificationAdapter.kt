package com.food.foodporterapplication.customer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.model.CustomerNotificationModel

class CustomerNotificationAdapter (val context: Context, private val offerList: List<CustomerNotificationModel>) :
    RecyclerView.Adapter<CustomerNotificationAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerNotificationAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.customer_notification_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomerNotificationAdapter.ViewHolder, position: Int) {

        val modelView = offerList[position]

    }

    override fun getItemCount(): Int {
        return offerList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}