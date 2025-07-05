package com.food.foodporterapplication.driver.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.food.foodporterapplication.R
import com.food.foodporterapplication.driver.model.DriverHomePageModel

class DriverHomePageAdapter  (val context: Context, private val favoriteList: List<DriverHomePageModel>) :
    RecyclerView.Adapter<DriverHomePageAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriverHomePageAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.driverhome_page_recyclerview, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: DriverHomePageAdapter.ViewHolder, position: Int) {
        val modelView = favoriteList[position]
        holder.tvCustomerName.text = modelView.customerName
        holder.tvDeliveryAddress.text = modelView.address
        holder.tvOrderStatus.text = modelView.status

    }

    override fun getItemCount(): Int {
        return favoriteList.size

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCustomerName: TextView = itemView.findViewById(R.id.tvCustomerName)
        val tvDeliveryAddress: TextView = itemView.findViewById(R.id.tvDeliveryAddress)
        val tvOrderStatus: TextView = itemView.findViewById(R.id.tvOrderStatus)

    }
}