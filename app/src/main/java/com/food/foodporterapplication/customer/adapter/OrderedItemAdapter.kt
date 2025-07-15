package com.food.foodporterapplication.customer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.fragment.myorderlist.model.MyOrderListResponse

class OrderedItemAdapter(
    val context: Context,
    private val orderList: List<MyOrderListResponse.Item>,
) :
    RecyclerView.Adapter<OrderedItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_item_quantity_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = orderList[position]

        val dishQuantity = "${item.quantity} × ${item.dishName}"
        holder.itemNamesText.text = dishQuantity
        holder.orderStatus.text = item.orderStatus

        when (item.orderStatus) {
            "Pending" -> {
                holder.orderStatus.setTextColor(ContextCompat.getColor(context,R.color.orange))
        }"Confirmed" ->{
            holder.orderStatus.setTextColor(ContextCompat.getColor(context,R.color.green))
        } "Cancelled" -> {
            holder.orderStatus.setTextColor(ContextCompat.getColor(context, R.color.red))
        }
    }

    val addonList = item.addons

    if (!addonList.isNullOrEmpty())
    {
        val addonText = addonList
            .filter { !it.addonName.isNullOrBlank() }
            .joinToString(", ") {
                val qty = it.quantity ?: 1
                "$qty × ${it.addonName}"
            }

        if (addonText.isNotBlank()) {
            holder.addOnListText.visibility = View.VISIBLE
            holder.addOnListText.text = addonText
        } else {
            holder.addOnListText.visibility = View.GONE
        }
    } else
    {
        holder.addOnListText.visibility = View.GONE
    }

}

override fun getItemCount(): Int {
    return orderList.size

}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val itemNamesText: TextView = itemView.findViewById(R.id.itemNamesText)
    val orderStatus: TextView = itemView.findViewById(R.id.orderStatusText)
    val addOnListText: TextView = itemView.findViewById(R.id.addonItemText)
}
}