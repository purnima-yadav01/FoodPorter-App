package com.food.foodporterapplication.customer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.model.AddAddressModel

class AddAddressesAdapter (val context: Context, private val offerList: List<AddAddressModel>) :
    RecyclerView.Adapter<AddAddressesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddAddressesAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.add_addresses_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddAddressesAdapter.ViewHolder, position: Int) {

        val modelView = offerList[position]
        holder.addressText.text = modelView.addAddressText

    }

    override fun getItemCount(): Int {
        return offerList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val addressText: TextView = itemView.findViewById(R.id.addressText)
    }
}