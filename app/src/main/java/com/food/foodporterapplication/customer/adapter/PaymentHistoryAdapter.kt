package com.food.foodporterapplication.customer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.model.PaymentMethodModel

class PaymentHistoryAdapter (val context: Context, private val favoriteList: List<PaymentMethodModel>) :
    RecyclerView.Adapter<PaymentHistoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PaymentHistoryAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.payment_history_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PaymentHistoryAdapter.ViewHolder, position: Int) {

        val modelView = favoriteList[position]

    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }
}