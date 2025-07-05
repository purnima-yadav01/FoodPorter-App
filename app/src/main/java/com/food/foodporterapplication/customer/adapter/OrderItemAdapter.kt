package com.food.foodporterapplication.customer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.model.OrderItemsModel

class OrderItemAdapter (private val items: List<OrderItemsModel>) :
    RecyclerView.Adapter<OrderItemAdapter.QuantityViewHolder>() {

    inner class QuantityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuantityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_item_quantity_layout, parent, false)
        return QuantityViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuantityViewHolder, position: Int) {
        val item = items[position]

    }

    override fun getItemCount(): Int = items.size
}